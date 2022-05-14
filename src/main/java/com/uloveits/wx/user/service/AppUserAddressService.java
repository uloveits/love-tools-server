package com.uloveits.wx.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.user.model.AppUserAddress;

import java.util.Map;

/**
 * @author lyrics
 */
public interface AppUserAddressService extends IService<AppUserAddress> {

    Boolean update(AppUserAddress appUserAddress);

    PageResult<AppUserAddress> getPage(Map<String, Object> param);

    PageResult<AppUserAddress> getList(Map<String, Object> param);
}
