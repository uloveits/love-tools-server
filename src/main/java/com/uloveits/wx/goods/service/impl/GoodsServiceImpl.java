package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.Constants;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.dao.GoodsMapper;
import com.uloveits.wx.goods.model.Goods;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.model.GoodsSku;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.goods.service.GoodsClassifyService;
import com.uloveits.wx.goods.service.GoodsService;
import com.uloveits.wx.goods.service.GoodsSkuService;
import com.uloveits.wx.goods.service.SkuService;
import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private FileService fileService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private GoodsClassifyService goodsClassifyService;

    @Autowired
    private SkuService skuService;

    @Override
    public Boolean add(Goods goods) {
        if(super.insert(goods)) {
            saveGoodsSku(goods);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Goods goods){
        if (super.updateById(goods)) {
            //先删除所有的值
            goodsSkuService.delete(new EntityWrapper<GoodsSku>().eq("goods_id",goods.getId()));
            saveGoodsSku(goods);
            return true;
        }
        return false;
    }


    @Override
    public PageResult<Goods> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<Goods> goods = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<Goods> wrapper = new EntityWrapper<>();

        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }
        // 模糊筛选商品分类
        if (param.get("goodsClassifyId") != null && StringUtil.isNotBlank(param.get("goodsClassifyId").toString())) {
            wrapper.eq("goods_classify_id", param.get("goodsClassifyId").toString());
        }
        // 模糊筛选商品名称
        if (param.get("goodsName") != null && StringUtil.isNotBlank(param.get("goodsName").toString())) {
            wrapper.like("name", param.get("goodsName").toString());
        }

        wrapper.orderBy("sort", true);

        super.selectPage(goods,wrapper);

        List<Goods> list = goods.getRecords();
        for(Goods _goods : list) {
            aggregation(_goods);
        }

        return new PageResult<>(list, goods.getTotal());
    }

    @Override
    public PageResult<Goods> getList(Map<String, Object> param) {

        EntityWrapper<Goods> wrapper = new EntityWrapper<>();

        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }
        // 模糊筛选商品分类
        if (param.get("goodsClassifyId") != null && StringUtil.isNotBlank(param.get("goodsClassifyId").toString())) {
            wrapper.eq("goods_classify_id", param.get("goodsClassifyId").toString());
        }
        // 模糊筛选商品名称
        if (param.get("goodsName") != null && StringUtil.isNotBlank(param.get("goodsName").toString())) {
            wrapper.like("name", param.get("goodsName").toString());
        }

        wrapper.orderBy("sort", true);

        List<Goods> list = super.selectList(wrapper);
        for(Goods _goods : list) {
            aggregation(_goods);
        }


        return new PageResult<>(list);
    }

    @Override
    public Goods detail(String goodsId) {
        Goods _goods = super.selectById(goodsId);
        return aggregation(_goods);
    }


    /**
     * 保存sku信息
     * @param goods
     */
    private void saveGoodsSku(Goods goods) {
        //保存sku的信息
        for(GoodsSku _goodsSku:goods.getSkuValue()) {
            //如果没有sku信息
            if(goods.getSkuType().equals(Constants.GOODS.NO_SKU)){
                //将fileId通过字符串方式储存到数据库
                String fileId = FncUtil.getFileId( _goodsSku.getFileList());
                _goodsSku.setUrlId(fileId);
                _goodsSku.setGoodsId(goods.getId());
                goodsSkuService.insert(_goodsSku);
            }else {
                //将fileId通过字符串方式储存到数据库
                String fileId = FncUtil.getFileId( _goodsSku.getFileList());
                _goodsSku.setUrlId(fileId);
                _goodsSku.setGoodsId(goods.getId());
                _goodsSku.setSkuClassifyId(goods.getSkuClassifyId());
                goodsSkuService.insert(_goodsSku);
            }
        }
    }

    /**
     * 聚合数据
     * @param list
     * @return
     */
    private Goods aggregation(Goods _goods) {
        //聚合文件
        List<File> _fileList = new ArrayList<>();
        List<Integer> _fileIds = FncUtil.getFileIds(_goods.getUrlId());
        if(_fileIds.size()>0){
            _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
        }
        _goods.setFileList(_fileList);

        //聚合分类
        GoodsClassify _goodsClassify = goodsClassifyService.selectById(_goods.getGoodsClassifyId());
        _goods.setClassify(_goodsClassify);

        //聚合sku信息
        if(StringUtil.isNotBlank(_goods.getSkuClassifyId())){
            List<Sku> _sku = skuService.selectSkuByClassifyId(_goods.getSkuClassifyId());
            _goods.setSku(_sku);
        }

        //聚合GoodsSku信息
        List<GoodsSku> _goodsSku = goodsSkuService.getGoodsSkuByGoodsId(_goods.getId());
        _goods.setSkuValue(_goodsSku);

        return _goods;
    }



}
