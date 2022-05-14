package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.system.model.Authorities;
import com.uloveits.wx.common.PageResult;

import java.util.List;
import java.util.Map;

public interface AuthoritiesService extends IService<Authorities> {

    List<String> listByUserId(Integer userId);

    List<String> listByRoleIds(List<Integer> roleId);

    List<String> listByRoleId(Integer roleId);

    PageResult<Map<String, Object>> getPage(Map<String, Object> param);

    PageResult<Map<String, Object>> getList(Map<String, Object> param);

}
