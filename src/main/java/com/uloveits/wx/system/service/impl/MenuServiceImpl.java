package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.system.dao.MenuMapper;
import com.uloveits.wx.system.service.MenuService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.system.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public PageResult<Menu> getPage(Map<String, Object> param) {
        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<Menu> menuPage = new Page<>(page, limit);
        EntityWrapper<Menu> wrapper = new EntityWrapper<>();

        // 模糊筛选角色名
        if (param.get("parentId") != null && StringUtil.isNotBlank(param.get("parentId").toString())) {
            wrapper.eq("parent_id", Integer.parseInt(param.get("parentId").toString()));
        }

        wrapper.orderBy("sort_number", true);

        super.selectPage(menuPage,wrapper);

        List<Menu> menuList = menuPage.getRecords();

        return new PageResult<>(menuList, menuPage.getTotal());
    }

    @Override
    public PageResult<Menu> getList(Map<String, Object> param) {

        List<Menu> list = super.selectList(new EntityWrapper<Menu>().orderBy("sort_number", true));

        return new PageResult<>(list);
    }
}
