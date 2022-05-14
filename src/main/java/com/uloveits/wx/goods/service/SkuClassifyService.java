package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.model.SkuClassify;
import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
public interface SkuClassifyService extends IService<SkuClassify> {

    Boolean add(SkuClassify skuClassify);

    Boolean update(SkuClassify skuClassify);

    PageResult<SkuClassify> getPage(Map<String, Object> param);

    PageResult<SkuClassify> getList(Map<String, Object> param);
}
