package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.GoodsClassify;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
public interface GoodsClassifyService extends IService<GoodsClassify> {

    Boolean add(GoodsClassify goodsClassify);

    Boolean update(GoodsClassify goodsClassify);

    PageResult<GoodsClassify> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<GoodsClassify> getList(Map<String, Object> param);

    List<GoodsClassify> getTreeList(Map<String, Object> param);

    List<Map<String, Object>> getSelectTree(Map<String, Object> param);


}
