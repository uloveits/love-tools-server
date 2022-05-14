package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.goods.model.SkuClassify;

import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
public interface SkuService extends IService<Sku> {

    Boolean add(Sku sku);

    Boolean update(Sku sku);

    PageResult<Sku> getPage(Map<String, Object> param);

    PageResult<Sku> getList(Map<String, Object> param);

    List<Sku> selectSkuByClassifyId(String classifyId);
}
