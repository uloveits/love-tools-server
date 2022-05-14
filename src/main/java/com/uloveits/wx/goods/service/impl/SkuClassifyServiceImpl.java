package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.goods.dao.SkuClassifyMapper;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.model.SkuClassify;
import com.uloveits.wx.goods.service.SkuClassifyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class SkuClassifyServiceImpl extends ServiceImpl<SkuClassifyMapper, SkuClassify> implements SkuClassifyService {


    @Override
    public Boolean add(SkuClassify skuClassify) {
        if(super.insert(skuClassify)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(SkuClassify skuClassify){
        if (super.updateById(skuClassify)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<SkuClassify> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<SkuClassify> skuClassify = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<SkuClassify> wrapper = new EntityWrapper<>();


        super.selectPage(skuClassify,wrapper);

        List<SkuClassify> list = skuClassify.getRecords();

        return new PageResult<>(list, skuClassify.getTotal());
    }

    @Override
    public PageResult<SkuClassify> getList(Map<String, Object> param) {

        EntityWrapper<SkuClassify> wrapper = new EntityWrapper<>();

        List<SkuClassify> list = super.selectList(wrapper);

        return new PageResult<>(list);
    }

}
