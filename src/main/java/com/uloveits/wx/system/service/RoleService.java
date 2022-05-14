package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.Role;

import java.util.Map;


public interface RoleService extends IService<Role> {

    PageResult<Role> getPage(Map<String, Object> param);

    PageResult<Role> getList(Map<String, Object> param);

    Boolean addMenu(Map<String, Object> param);


}
