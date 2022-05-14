package com.uloveits.wx.setting.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.setting.model.AppNavigate;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface AppNavigateService extends IService<AppNavigate> {

    Boolean add(AppNavigate appNavigate);

    Boolean update(AppNavigate appNavigate);

    PageResult<AppNavigate> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<AppNavigate> getList(Map<String, Object> param);
}
