package com.uloveits.wx.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.Constants;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.support.pay.WxPay;
import com.uloveits.wx.common.support.pay.WxPayment;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.model.Cart;
import com.uloveits.wx.goods.model.Goods;
import com.uloveits.wx.goods.model.GoodsSku;
import com.uloveits.wx.goods.service.CartService;
import com.uloveits.wx.goods.service.GoodsService;
import com.uloveits.wx.goods.service.GoodsSkuService;
import com.uloveits.wx.order.dao.OrderMapper;
import com.uloveits.wx.order.model.Order;
import com.uloveits.wx.order.model.OrderGoods;
import com.uloveits.wx.order.service.OrderGoodsService;
import com.uloveits.wx.order.service.OrderService;

import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;
import com.uloveits.wx.user.service.AppUserAddressService;
import com.uloveits.wx.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private AppUserAddressService appUserAddressService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private FileService fileService;

    @Override
    public String add(Order order) {
        //时间字符串生成
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String orderNo = "Wx" + format.format(new Date());
        order.setNo(orderNo);
        if (super.insert(order)) {
            List<OrderGoods> orderGoodsList = order.getOrderGoods();
            for(OrderGoods orderGoods : orderGoodsList) {
                orderGoods.setOrderId(order.getId());
                orderGoodsService.insert(orderGoods);
                //删除购物车
                cartService.delete(
                        new EntityWrapper<Cart>()
                        .eq("goods_id",orderGoods.getGoodsId())
                        .eq("sku_value_ids",orderGoods.getSkuValueIds())
                );
                //减少库存
                goodsSkuService.updateStock(orderGoods.getGoodsId(),orderGoods.getSkuValueIds(),orderGoods.getCount(),Constants.REDUCE_STOCK);
            }
            return order.getId();
        }
        return "0";
    }

    @Override
    public Boolean update(Order order){
        if (super.updateById(order)) {
            return true;
        }
        return false;
    }


    @Override
    public Boolean cancel(Map<String, Object> param){
        String orderId = param.get("id").toString();
        Order order = detail(orderId);
        //更改订单状态
        order.setStatus(Constants.ORDER.STATUS.CANCEL);
        super.updateById(order);
        //调用微信取消订单接口
        String result = WxPay.closeOrder(orderId);
        Map<String, String> resultMap = WxPayment.xmlToMap(result);
        if (WxPayment.codeIsOK(resultMap.get("result_code"))) {
            //添加商品库存
            List<OrderGoods> orderGoods = order.getOrderGoods();
            for (OrderGoods entity : orderGoods) {
                goodsSkuService.updateStock(entity.getGoodsId(),entity.getSkuValueIds(),entity.getCount(),Constants.ADD_STOCK);
            }
            return true;
        }

        return false;
    }

    @Override
    public Boolean send(Map<String, Object> param){
        String orderId = param.get("id").toString();
        Order order = super.selectById(orderId);
        //快递公司Id
        order.setExpressId(param.get("expressId").toString());
        //快递单号
        order.setExpressNo(param.get("expressNo").toString());

        if (null != order.getStatus() && order.getStatus().equals(Constants.ORDER.STATUS.WAIT_SEND)) {
            order.setStatus(Constants.ORDER.STATUS.WAIT_RECEIVE);
            super.updateById(order);
            return true;
        }
        return false;
    }

    @Override
    public Boolean confirm(Map<String, Object> param){
        String orderId = param.get("id").toString();
        Order order = super.selectById(orderId);
        if (null != order.getStatus() && order.getStatus().equals(Constants.ORDER.STATUS.WAIT_RECEIVE)) {
            order.setStatus(Constants.ORDER.STATUS.WAIT_COMMENT);
            super.updateById(order);
            return true;
        }
        return false;
    }

    @Override
    public Integer selectCountByStatus(String userId,Integer status){
        return super.selectCount(
                new EntityWrapper<Order>()
                .eq("user_id",userId)
                .eq("order_status",status)
        );
    }

    @Override
    public PageResult<Order> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<Order> order = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<Order> wrapper = new EntityWrapper<>();

        // 模糊筛选订单编号
        if (param.get("no") != null && StringUtil.isNotBlank(param.get("no").toString())) {
            wrapper.like("order_no", param.get("no").toString());
        }
        // 模糊筛选用户
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.like("user_id", param.get("userId").toString());
        }

        // 模糊筛选订单状态
        if (param.get("status") != null && StringUtil.isNotBlank(param.get("status").toString())) {
            wrapper.eq("order_status", Integer.parseInt(param.get("status").toString()));
        }

        // 模糊筛选订单状态
        if (param.get("index") != null && StringUtil.isNotBlank(param.get("index").toString())) {
            if(!param.get("index").toString().equals("0")){
                wrapper.eq("order_status", Integer.parseInt(param.get("index").toString()));
            }
        }
        //删除标识
        wrapper.eq("is_delete", Constants.NO_DELETE);

        wrapper.orderBy("create_time", true);

        super.selectPage(order,wrapper);

        List<Order> list = order.getRecords();
        for(Order _order : list) {
            aggregation(_order);
        }

        return new PageResult<>(list, order.getTotal());
    }

    @Override
    public PageResult<Order> getList(Map<String, Object> param) {

        EntityWrapper<Order> wrapper = new EntityWrapper<>();

        // 模糊筛选订单编号
        if (param.get("no") != null && StringUtil.isNotBlank(param.get("no").toString())) {
            wrapper.like("order_no", param.get("no").toString());
        }
        // 模糊筛选用户
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.like("user_id", param.get("userId").toString());
        }

        // 模糊筛选订单状态
        if (param.get("status") != null && StringUtil.isNotBlank(param.get("status").toString())) {
            wrapper.eq("order_status", Integer.parseInt(param.get("status").toString()));
        }

        // 模糊筛选订单状态
        if (param.get("index") != null && StringUtil.isNotBlank(param.get("index").toString())) {
            if(!param.get("index").toString().equals("0")){
                wrapper.eq("order_status", Integer.parseInt(param.get("index").toString()));
            }
        }

        //删除标识
        wrapper.eq("is_delete", Constants.NO_DELETE);
        wrapper.orderBy("create_time", true);

        List<Order> list = super.selectList(wrapper);
        for(Order _order : list) {
            aggregation(_order);
        }


        return new PageResult<>(list);
    }

    @Override
    public Order detail(String orderId) {
        Order _order = super.selectById(orderId);
        return aggregation(_order);
    }

    /**
     * 聚合数据
     * @param list
     * @return
     */
    private Order aggregation(Order _order) {
        //聚合地址
        AppUserAddress address = appUserAddressService.selectById(_order.getAddressId());
        _order.setAddress(address);

        //聚合用户信息
        AppUser user = appUserService.selectById(_order.getUserId());
        _order.setUser(user);

        //聚合订单商品
        List<OrderGoods> orderGoods = orderGoodsService.selectList(new EntityWrapper<OrderGoods>().eq("order_id",_order.getId()));
        for(OrderGoods _orderGoods : orderGoods) {
            //聚合商品信息
            Goods _goods = goodsService.selectById(_orderGoods.getGoodsId());
            //聚合商品信息文件
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_goods.getUrlId());
            if(_fileIds.size()>0){
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _goods.setFileList(_fileList);
            _orderGoods.setGoods(_goods);
        }
        _order.setOrderGoods(orderGoods);

        return _order;
    }



}
