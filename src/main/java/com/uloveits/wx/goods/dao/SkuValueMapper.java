package com.uloveits.wx.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uloveits.wx.goods.model.SkuValue;

/**
 * @author lyrics
 */
public interface SkuValueMapper extends BaseMapper<SkuValue> {
    Integer deleteBySkuId(String skuId);
}
