package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnTravel;
import com.uloveits.wx.common.PageResult;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnTravelService extends IService<ColumnTravel> {


    Boolean update(ColumnTravel columnTravel);

    PageResult<ColumnTravel> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnTravel> getList(Map<String, Object> param);


}
