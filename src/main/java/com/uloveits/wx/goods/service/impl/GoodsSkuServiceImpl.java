package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.Constants;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.goods.dao.GoodsSkuMapper;
import com.uloveits.wx.goods.model.GoodsSku;
import com.uloveits.wx.goods.service.GoodsSkuService;
import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

    @Autowired
    private FileService fileService;

    @Override
    public List<GoodsSku> getGoodsSkuByGoodsId(String goodsId) {

        EntityWrapper<GoodsSku> wrapper = new EntityWrapper<>();
        wrapper.eq("goods_id",goodsId);
        List<GoodsSku> _goodsSku = super.selectList(wrapper);

        return aggregation(_goodsSku);
    }

    @Override
    public void updateStock(String goodsId,String skuValueIds,Integer count,Integer type) {
        GoodsSku goodsSku = super.selectOne(
                new EntityWrapper<GoodsSku>()
                        .eq("goods_id",goodsId)
                        .eq("sku_value_ids",skuValueIds)
        );
        if(Constants.ADD_STOCK.equals(type)){
            //增加库存
            Integer newStock = Integer.parseInt(goodsSku.getStock()) + count;
            goodsSku.setStock(newStock.toString());
            super.updateById(goodsSku);
        }else if(Constants.REDUCE_STOCK .equals(type)) {
            //减少库存
            if(Integer.parseInt(goodsSku.getStock())>=count){
                Integer newStock = Integer.parseInt(goodsSku.getStock()) - count;
                goodsSku.setStock(newStock.toString());
                super.updateById(goodsSku);
            }else{
                throw new BusinessException("库存不足");
            }
        }

    }

    /**
     * 聚合数据
     * @param list
     * @return
     */
    private List<GoodsSku> aggregation(List<GoodsSku> list ) {
        for(GoodsSku _goodsSku : list) {
            //聚合文件
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_goodsSku.getUrlId());
            if(_fileIds.size()>0){
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _goodsSku.setFileList(_fileList);
        }
        return list;
    }

}
