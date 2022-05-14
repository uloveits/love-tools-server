package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.system.dao.UserMapper;
import com.uloveits.wx.system.model.Role;
import com.uloveits.wx.system.model.User;
import com.uloveits.wx.system.model.UserRole;
import com.uloveits.wx.system.service.RoleService;
import com.uloveits.wx.system.service.UserRoleService;
import com.uloveits.wx.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public User getByUserName(String userName) {
        return baseMapper.getByUserName(userName);
    }

    /**
     * 查询用户列表
     * @param param
     * @return
     */
    @Override
    public PageResult<User> getData(Map<String, Object> param){

        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<User> userPage = new Page<>(page, limit);
        EntityWrapper<User> wrapper = new EntityWrapper<>();

        // 模糊筛选用户名
        if (param.get("nickName") != null && StringUtil.isNotBlank(param.get("nickName").toString())) {
            wrapper.like("nick_name", param.get("nickName").toString());
        }
        //查询用户禁用状态
        if (param.get("state") != null && StringUtil.isNotBlank(param.get("state").toString())) {
            wrapper.eq("state", param.get("state").toString());
        }
        wrapper.orderBy("create_time", true);
        super.selectPage(userPage, wrapper);
        List<User> userList = userPage.getRecords();
        // 关联查询role
        List<Integer> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        List<UserRole> userRoles = userRoleService.selectList(new EntityWrapper().in("user_id", userIds));
        List<Role> roles = roleService.selectList(null);
        for (User one : userList) {
            List<Role> tempUrs = new ArrayList<>();
            for (UserRole ur : userRoles) {
                if (one.getUserId().equals(ur.getUserId())) {
                    for (Role r : roles) {
                        if (ur.getRoleId().equals(r.getRoleId())) {
                            tempUrs.add(r);
                        }
                    }
                }
            }
            one.setRoles(tempUrs);
        }


        return new PageResult<>(userList, userPage.getTotal());
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Boolean add(User param) {
        User user = param;
        String[] rolesId = user.getRolesId();
        user.setPassword(DigestUtils.md5DigestAsHex("888888".getBytes()));
        user.setEmailVerified(0);
        if (super.insert(user)) {
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : rolesId) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(Integer.parseInt(roleId));
                userRoles.add(userRole);
            }

            if (!userRoleService.insertBatch(userRoles)) {
                throw new BusinessException("添加失败");
            }
            return true;
        }
        return false;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public Boolean update(User param) {
        User user = param;
        String[] rolesId = user.getRolesId();

        if (super.updateById(user)) {
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : rolesId) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(user.getUserId());
                userRoles.add(userRole);
            }
            userRoleService.delete(new EntityWrapper<UserRole>().eq("user_id", user.getUserId()));
            if (!userRoleService.insertBatch(userRoles)) {
                throw new BusinessException("修改失败");
            }
            return true;
        }
        return false;
    }


}
