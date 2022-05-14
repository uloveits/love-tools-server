package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.goods.model.SkuValue;

import java.util.Map;

/**
 * @author lyrics
 */
public interface SkuValueService extends IService<SkuValue> {

    Integer deleteBySkuId(String skuId);
}
