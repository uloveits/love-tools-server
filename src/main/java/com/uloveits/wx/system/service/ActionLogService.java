package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.ActionLog;

import java.util.Map;

public interface ActionLogService extends IService<ActionLog> {

    PageResult<ActionLog> getPage(Map<String, Object> param);

    PageResult<ActionLog> getList(Map<String, Object> param);
}
