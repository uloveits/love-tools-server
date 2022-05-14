package com.uloveits.wx.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uloveits.wx.system.model.RoleMenu;

import java.util.List;


public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    Integer deleteByRoleId(Integer roleId);

    List<RoleMenu> getMenuByRoleId(Integer roleId);
}
