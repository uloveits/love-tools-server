package com.uloveits.wx.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uloveits.wx.system.model.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int deleteTrash();
}
