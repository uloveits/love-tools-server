package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.goods.model.GoodsSku;

import java.util.List;


/**
 * @author lyrics
 */
public interface GoodsSkuService extends IService<GoodsSku> {
    /**
     * 通过商品Id获得商品Sku
     * @param goodsId
     * @return
     */
    List<GoodsSku> getGoodsSkuByGoodsId(String goodsId);

    void updateStock(String goodsId,String skuValueIds,Integer count,Integer type);

}
