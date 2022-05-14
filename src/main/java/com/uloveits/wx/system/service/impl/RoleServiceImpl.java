package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.system.dao.RoleMapper;
import com.uloveits.wx.system.model.Role;
import com.uloveits.wx.system.model.RoleMenu;
import com.uloveits.wx.system.service.RoleMenuService;
import com.uloveits.wx.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 角色
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public PageResult<Role> getPage(Map<String, Object> param) {

        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<Role> rolePage = new Page<>(page, limit);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();

        // 模糊筛选角色名
        if (param.get("roleName") != null && StringUtil.isNotBlank(param.get("roleName").toString())) {
            wrapper.like("role_name", param.get("roleName").toString());
        }

        wrapper.orderBy("create_time", true);

        super.selectPage(rolePage,wrapper);

        List<Role> roleList = rolePage.getRecords();

        return new PageResult<>(roleList, rolePage.getTotal());
    }

    @Override
    public PageResult<Role> getList(Map<String, Object> param) {

        List<Role> list = super.selectList(new EntityWrapper<Role>().orderBy("create_time", true));

        return new PageResult<>(list);
    }

    @Override
    public Boolean addMenu(Map<String, Object> param) {
        String roleId = param.get("roleId").toString();
        String[] menuIds = param.get("menuIds").toString().split(",");

        List<RoleMenu> roleMenus = new ArrayList<>();

        for (String menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(Integer.parseInt(roleId));
            roleMenu.setMenuId(Integer.parseInt(menuId));
            roleMenus.add(roleMenu);
        }
        //先删除该角色的菜单权限
        roleMenuService.deleteByRoleId(Integer.parseInt(roleId));

        if (!roleMenuService.insertBatch(roleMenus)) {
            throw new BusinessException("添加失败");
        }
        return true;
    }



}
