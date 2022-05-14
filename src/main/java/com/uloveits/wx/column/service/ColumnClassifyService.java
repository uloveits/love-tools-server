package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnClassify;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.GoodsClassify;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnClassifyService extends IService<ColumnClassify> {


    Boolean update(ColumnClassify columnClassify);

    PageResult<ColumnClassify> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnClassify> getList(Map<String, Object> param);

}
