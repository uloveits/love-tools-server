package com.uloveits.wx.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.user.dao.AppUserMapper;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.service.LoginService;
import org.springframework.stereotype.Service;


/**
 * 用户Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class LoginServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements LoginService {

}
