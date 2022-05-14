package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.LoginLog;
import java.util.Map;

public interface LoginLogService extends IService<LoginLog> {

    PageResult<LoginLog> getPage(Map<String, Object> param);

    PageResult<LoginLog> getList(Map<String, Object> param);
}
