package com.uloveits.wx.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.system.model.Menu;
import com.uloveits.wx.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.*;

/**
 * @author lyrics
 */
@Api(value = "菜单管理", tags = "menu")
@RestController
@RequestMapping("${api.version}/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    @RequiresPermissions("get:/v1/menu/all/tree")
    @ApiOperation(value = "查询所有菜单树结构")
    @GetMapping("/all/tree")
    public JsonResult treeList(HttpServletRequest request) {
        super.insertActionLog(request,"get:/v1/menu/all/tree","{}");
        List<Menu> list = menuService.selectList(new EntityWrapper<Menu>().orderBy("sort_number", true));
        return JsonResult.ok().put("data", getMenuTree(list, -1));
    }

    @RequiresPermissions("post:/v1/menu/all/page")
    @ApiOperation(value = "通过parentId查询菜单信息")
    @PostMapping("/all/page")
    public PageResult<Menu> getMenuByParentId(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/menu/all/page",param.toString());
        if (param.get("page") == null || param.get("limit") == null) {
            return menuService.getList(param);
        }else {
            return menuService.getPage(param);
        }
    }

    // 递归转化树形菜单
    private List<Map<String, Object>> getMenuTree(List<Menu> menus, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Menu temp = menus.get(i);
            if (parentId.intValue() == temp.getParentId().intValue()) {
                Map<String, Object> map = new HashMap<>();
                map.put("key", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
                map.put("title", temp.getMenuName());
                map.put("id", temp.getMenuId());
                map.put("icon", temp.getMenuIcon());
                map.put("children", getMenuTree(menus, menus.get(i).getMenuId()));
                list.add(map);
            }
        }
        return list;
    }

    @RequiresPermissions("post:/v1/menu/add")
    @ApiOperation(value = "添加菜单")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request,@RequestBody Menu menu) {
        super.insertActionLog(request,"post:/v1/menu/add",menu.toString());
        if (menuService.insert(menu)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @RequiresPermissions("post:/v1/menu/update")
    @ApiOperation(value = "修改菜单")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request,@RequestBody Menu menu) {
        super.insertActionLog(request,"post:/v1/menu/update",menu.toString());
        if (menuService.updateById(menu)) {
            return JsonResult.ok("修改成功！");
        }
        return JsonResult.error("修改失败！");
    }

    @RequiresPermissions("post:/v1/menu/delete")
    @ApiOperation(value = "删除菜单")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/menu/delete",param.toString());
        if (menuService.deleteById(Integer.parseInt(param.get("menuId").toString()))) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
}
