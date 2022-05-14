package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.uloveits.wx.goods.dao.SkuValueMapper;

import com.uloveits.wx.goods.model.SkuValue;

import com.uloveits.wx.goods.service.SkuValueService;
import org.springframework.stereotype.Service;



/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class SkuValueServiceImpl extends ServiceImpl<SkuValueMapper, SkuValue> implements SkuValueService {

    @Override
    public Integer deleteBySkuId(String skuId) {
       return baseMapper.deleteBySkuId(skuId);
    }
}
