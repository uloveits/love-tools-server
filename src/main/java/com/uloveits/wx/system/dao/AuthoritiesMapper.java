package com.uloveits.wx.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uloveits.wx.system.model.Authorities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthoritiesMapper extends BaseMapper<Authorities> {

    List<String> listByUserId(Integer userId);

    List<String> listByRoleId(@Param("roleIds") List<Integer> roleIds);
}
