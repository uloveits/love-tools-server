package com.uloveits.wx.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.JSONUtil;
import com.uloveits.wx.system.model.Authorities;
import com.uloveits.wx.system.model.RoleAuthorities;
import com.uloveits.wx.system.service.AuthoritiesService;
import com.uloveits.wx.system.service.RoleAuthoritiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
@Api(value = "权限管理", tags = "authorities")
@RestController
@RequestMapping("${api.version}/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private RoleAuthoritiesService roleAuthoritiesService;

    @RequiresPermissions("post:/v1/authorities/sync")
    @ApiOperation(value = "同步权限")
    @PostMapping("/sync")
    public JsonResult add(HttpServletRequest request,@RequestBody Map<String,List> param) {
        super.insertActionLog(request,"post:/v1/authorities/sync",param.toString());

        JSONArray jsonArray=new JSONArray(param.get("authList"));
        List<Authorities> list = JSONUtil.parseArray(jsonArray.toString(), Authorities.class);
        authoritiesService.delete(null);
        authoritiesService.insertBatch(list);
        roleAuthoritiesService.deleteTrash();
        return JsonResult.ok("同步成功");
    }

    @RequiresPermissions("post:/v1/authorities/all")
    @ApiOperation(value = "查询所有权限")
    @PostMapping("/all")
    public PageResult<Map<String, Object>> list(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/authorities/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return authoritiesService.getList(param);
        }else {
            return authoritiesService.getPage(param);
        }
    }

    @RequiresPermissions("post:/v1/authorities/role/add")
    @ApiOperation(value = "给角色添加权限")
    @PostMapping("/role/add")
    public JsonResult addRoleAuth(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/authorities/role/add",param.toString());

        RoleAuthorities roleAuth = new RoleAuthorities();
        roleAuth.setRoleId(Integer.parseInt(param.get("roleId").toString()));
        roleAuth.setAuthority(param.get("authId").toString());
        if (roleAuthoritiesService.insert(roleAuth)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @RequiresPermissions("delete:/v1/authorities/role/delete")
    @ApiOperation(value = "移除角色权限")
    @PostMapping("/role/delete")
    public JsonResult deleteRoleAuth(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"delete:/v1/authorities/role/delete",param.toString());

        if (roleAuthoritiesService.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", Integer.parseInt(param.get("roleId").toString())).eq("authority", param.get("authId").toString()))) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }
}
