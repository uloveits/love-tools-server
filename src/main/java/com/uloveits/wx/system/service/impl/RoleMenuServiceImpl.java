package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.system.dao.RoleMenuMapper;
import com.uloveits.wx.system.model.RoleMenu;
import com.uloveits.wx.system.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Administrator on 2018-12-19 下午 4:10.
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public Integer deleteByRoleId(Integer roleId) {
        return baseMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<RoleMenu> getMenuByRoleId(Integer roleId) {
        return baseMapper.getMenuByRoleId(roleId);
    }

}
