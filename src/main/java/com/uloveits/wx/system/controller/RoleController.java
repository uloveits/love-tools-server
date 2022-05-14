package com.uloveits.wx.system.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.system.model.Role;
import com.uloveits.wx.system.model.RoleMenu;
import com.uloveits.wx.system.service.RoleMenuService;
import com.uloveits.wx.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

@Api(value = "角色管理", tags = "role")
@RestController
@RequestMapping("${api.version}/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @RequiresPermissions("post:/v1/role/all")
    @ApiOperation(value = "查询所有角色")
    @PostMapping("/all")
    public PageResult<Role> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/role/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return roleService.getList(param);
        }else {
            return roleService.getPage(param);
        }

    }

    @RequiresPermissions("post:/v1/role/add")
    @ApiOperation(value = "添加角色")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request,@RequestBody Role role) {
        super.insertActionLog(request,"post:/v1/role/add",role.toString());

        if (roleService.insert(role)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @RequiresPermissions("post:/v1/role/update")
    @ApiOperation(value = "修改角色")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request,@RequestBody Role role) {
        super.insertActionLog(request,"post:/v1/role/update",role.toString());

        if (roleService.updateById(role)) {
            return JsonResult.ok("修改成功！");
        }
        return JsonResult.error("修改失败！");
    }

    @RequiresPermissions("post:/v1/role/delete")
    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/role/delete",param.toString());

        if (roleService.deleteById(Integer.parseInt(param.get("roleId").toString()))) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    @RequiresPermissions("post:/v1/role/getMenu")
    @ApiOperation(value = "查询角色的菜单权限")
    @PostMapping("/getMenu")
    public  JsonResult getMenuByRoleId(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/role/getMenu",param.toString());

        List<RoleMenu> roleMenus = roleMenuService.getMenuByRoleId(Integer.parseInt(param.get("roleId").toString()));

        return JsonResult.ok("操作成功").put("data",roleMenus);
    }

    @RequiresPermissions("post:/v1/role/addMenu")
    @ApiOperation(value = "角色绑定菜单权限")
    @PostMapping("/addMenu")
    public JsonResult addMenu(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/role/addMenu",param.toString());

        if (roleService.addMenu(param)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

}
