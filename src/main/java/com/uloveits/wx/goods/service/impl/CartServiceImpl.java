package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.dao.CartMapper;

import com.uloveits.wx.goods.model.*;
import com.uloveits.wx.goods.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public Boolean add(Cart cart) {
        //先检查购物车有没有相同的商品
        Cart _cart = super.selectOne(
                new EntityWrapper<Cart>()
                        .eq("goods_id",cart.getGoodsId())
                        .eq("sku_value_ids",cart.getSkuValueIds())
        );
        if(_cart == null) {
            //说明没有 执行加入操作
            if(super.insert(cart)) {
                return true;
            }
        }else {
            //说明有 追加数量
            Integer newCount = cart.getCount() + _cart.getCount();
            _cart.setCount(newCount);
            if( super.updateById(_cart)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean checked(Cart cart){
        if(super.updateById(cart)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean checkedAll(Map<String, Object> param){
        String[] ids = param.get("ids").toString().split(",");
        Boolean res = false;
        for(String id:ids){
            Cart cart = new Cart();
            cart.setId(id);
            cart.setChecked(Integer.parseInt(param.get("checked").toString()));
            if(super.updateById(cart)) {
                res = true;
            }else {
                res = false;
            }
        }
        return res;
    }


    @Override
    public PageResult<Cart> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<Cart> cart = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<Cart> wrapper = new EntityWrapper<>();

        // 筛选
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.eq("user_id", param.get("userId").toString());
        }


        wrapper.orderBy("create_time", true);

        super.selectPage(cart,wrapper);

        List<Cart> list = cart.getRecords();
        for(Cart _cart : list) {
            aggregation(_cart);
        }

        return new PageResult<>(list, cart.getTotal());
    }

    @Override
    public PageResult<Cart> getList(Map<String, Object> param) {

        EntityWrapper<Cart> wrapper = new EntityWrapper<>();

        // 筛选
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.eq("user_id", param.get("userId").toString());
        }

        wrapper.orderBy("create_time", true);


        List<Cart> list = super.selectList(wrapper);
        for(Cart _cart : list) {
            aggregation(_cart);
        }


        return new PageResult<>(list);
    }



    /**
     * 聚合数据
     * @param list
     * @return
     */
    private Goods aggregation(Cart _cart) {
        //聚合商品信息
        Goods _goods = goodsService.detail(_cart.getGoodsId());
        _cart.setGoods(_goods);
        return _goods;
    }
}
