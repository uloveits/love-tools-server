package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnPromiseCard;
import com.uloveits.wx.common.PageResult;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnPromiseCardService extends IService<ColumnPromiseCard> {


    Boolean update(ColumnPromiseCard columnPromiseCard);

    PageResult<ColumnPromiseCard> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnPromiseCard> getList(Map<String, Object> param);


}
