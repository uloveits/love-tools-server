package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.User;

import java.util.Map;

public interface UserService extends IService<User> {

    User getByUserName(String userName);

    PageResult<User> getData(Map<String, Object> param);

    Boolean add(User user);

    Boolean update(User user);

}
