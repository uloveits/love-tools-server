package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnRemember;
import com.uloveits.wx.column.model.ColumnRememberSubscribe;
import com.uloveits.wx.common.PageResult;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnRememberSubscribeService extends IService<ColumnRememberSubscribe> {


    Boolean update(Map<String, Object> param);

    PageResult<ColumnRememberSubscribe> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnRememberSubscribe> getList(Map<String, Object> param);


}
