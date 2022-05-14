package com.uloveits.wx.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uloveits.wx.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUserName(String userName);
}
