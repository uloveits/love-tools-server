package com.uloveits.wx.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.Menu;

import java.util.Map;

public interface MenuService extends IService<Menu> {

    PageResult<Menu> getPage(Map<String, Object> param);

    PageResult<Menu> getList(Map<String, Object> param);
}
