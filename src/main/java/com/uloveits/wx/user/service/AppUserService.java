package com.uloveits.wx.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;

import java.util.Map;

/**
 * @author lyrics
 */
public interface AppUserService extends IService<AppUser> {

    PageResult<AppUser> getPage(Map<String, Object> param);

    PageResult<AppUser> getList(Map<String, Object> param);

    Boolean bind(Map<String, Object> param);

    AppUser detail(Map<String, Object> param);
}
