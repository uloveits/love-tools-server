package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnRemember;
import com.uloveits.wx.common.PageResult;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnRememberService extends IService<ColumnRemember> {


    Boolean update(ColumnRemember columnRemember);

    PageResult<ColumnRemember> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnRemember> getList(Map<String, Object> param);

    void sendTemplateMessage();

}
