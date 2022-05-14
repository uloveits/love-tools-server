package com.uloveits.wx.setting.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.setting.model.AppBanner;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface AppBannerService extends IService<AppBanner> {

    Boolean add(AppBanner appBanner);

    Boolean update(AppBanner appBanner);

    PageResult<AppBanner> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<AppBanner> getList(Map<String, Object> param);
}
