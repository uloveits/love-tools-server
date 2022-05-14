package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Goods;
import com.uloveits.wx.goods.model.GoodsClassify;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface GoodsService extends IService<Goods> {

    Boolean add(Goods goods);

    Boolean update(Goods goods);

    PageResult<Goods> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<Goods> getList(Map<String, Object> param);

    Goods detail(String goodsId);

}
