package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.system.model.Authorities;
import com.uloveits.wx.system.service.AuthoritiesService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.ReflectUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.system.dao.AuthoritiesMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthoritiesServiceImpl extends ServiceImpl<AuthoritiesMapper, Authorities> implements AuthoritiesService {

    @Override
    public List<String> listByUserId(Integer userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public List<String> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return baseMapper.listByRoleId(roleIds);
    }

    @Override
    public List<String> listByRoleId(Integer roleId) {
        List<Integer> roleIds = new ArrayList<>();
        if (roleId != null) {
            roleIds.add(roleId);
        }
        return listByRoleIds(roleIds);
    }

    @Override
    public PageResult<Map<String, Object>> getPage(Map<String, Object> param) {

        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<Authorities> authPage = new Page<>(page, limit);
        EntityWrapper<Authorities> wrapper = new EntityWrapper<>();

        // 模糊筛选角色名
        if (param.get("keyword") != null && StringUtil.isNotBlank(param.get("keyword").toString())) {
            wrapper.like("authority_name", param.get("keyword").toString());
        }

        wrapper.orderBy("sort", true);

        super.selectPage(authPage,wrapper);

        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authorities> authorities = authPage.getRecords();
        // 回显选中状态
        List<String> roleAuths = new ArrayList<>();
        if(param.get("roleId") != null){
            roleAuths = listByRoleId(Integer.parseInt(param.get("roleId").toString()));
        }else {
            roleAuths = listByRoleId(null);
        }
        maps = roleAuthChecked(authorities,roleAuths);
        return new PageResult<>(maps, authPage.getTotal());
    }

    @Override
    public PageResult<Map<String, Object>> getList(Map<String, Object> param) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authorities> authorities = super.selectList(new EntityWrapper<Authorities>().orderBy("sort", true));
        // 回显选中状态
        List<String> roleAuths = new ArrayList<>();
        if(param.get("roleId") != null){
            roleAuths = listByRoleId(Integer.parseInt(param.get("roleId").toString()));
        }else {
            roleAuths = listByRoleId(null);
        }
        maps = roleAuthChecked(authorities,roleAuths);
        return new PageResult<>(maps);
    }


    private List<Map<String, Object>> roleAuthChecked( List<Authorities> authorities, List<String> roleAuths) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Authorities one : authorities) {
            Map<String, Object> map = ReflectUtil.objectToMap(one);
            map.put("checked", false);
            for (String roleAuth : roleAuths) {
                if (one.getAuthority().equals(roleAuth)) {
                    map.put("checked", true);
                    break;
                }
            }
            maps.add(map);
        }
        return maps;
    }

}
