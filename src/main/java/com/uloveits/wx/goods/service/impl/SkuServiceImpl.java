package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;

import com.uloveits.wx.goods.dao.SkuMapper;
import com.uloveits.wx.goods.model.Sku;

import com.uloveits.wx.goods.model.SkuValue;
import com.uloveits.wx.goods.service.SkuService;
import com.uloveits.wx.goods.service.SkuValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Autowired
    private SkuValueService skuValueService;


    @Override
    public Boolean add(Sku sku) {
        if(super.insert(sku)) {
            //将Sku的Value的值分别存入表中
            saveSkuValue(sku);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Sku sku){
        if (super.updateById(sku)) {
            //先删除所有的值
            skuValueService.deleteBySkuId(sku.getId());
            saveSkuValue(sku);
            return true;
        }
        return false;
    }


    @Override
    public PageResult<Sku> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<Sku> sku = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<Sku> wrapper = new EntityWrapper<>();

        // 通过规格分类筛选
        if (param.get("classifyId") != null && StringUtil.isNotBlank(param.get("classifyId").toString())) {
            wrapper.eq("classify_id", param.get("classifyId").toString());
        }

        wrapper.orderBy("sort", true);

        super.selectPage(sku,wrapper);

        List<Sku> list = sku.getRecords();

        return new PageResult<>(list, sku.getTotal());
    }

    @Override
    public PageResult<Sku> getList(Map<String, Object> param) {

        EntityWrapper<Sku> wrapper = new EntityWrapper<>();

        // 通过规格分类筛选
        if (param.get("classifyId") != null && StringUtil.isNotBlank(param.get("classifyId").toString())) {
            wrapper.eq("classify_id", param.get("classifyId").toString());
        }

        wrapper.orderBy("sort", true);

        List<Sku> list = super.selectList(wrapper);

        //聚合skuValue的值
        for(Sku _sku:list){
            List<SkuValue> _skuValue = skuValueService.selectList(new EntityWrapper<SkuValue>().eq("sku_id",_sku.getId()));
            _sku.setValueList(_skuValue);
        }

        return new PageResult<>(list);
    }


    @Override
    public List<Sku> selectSkuByClassifyId(String classifyId) {

        List<Sku> sku = super.selectList(new EntityWrapper<Sku>().eq("classify_id",classifyId));


        for(Sku _sku:sku) {
            List<SkuValue> _skuValue = skuValueService.selectList(new EntityWrapper<SkuValue>().eq("sku_id",_sku.getId()));
            _sku.setValueList(_skuValue);
        }
        return sku;
    }


    private void saveSkuValue(Sku sku){
        String[] values = sku.getValue().split(",");
        for(String value :values) {
            SkuValue skuValue = new SkuValue();
            skuValue.setSkuValue(value);
            skuValue.setSkuId(sku.getId());
            skuValueService.insert(skuValue);
        }
    }

}
