package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.system.model.RoleMenu;

import java.util.List;

/**
 * Created by Lyrics on 2019-11-09.
 */
public interface RoleMenuService extends IService<RoleMenu> {

    Integer deleteByRoleId(Integer roleId);

    List<RoleMenu> getMenuByRoleId(Integer roleId);
}
