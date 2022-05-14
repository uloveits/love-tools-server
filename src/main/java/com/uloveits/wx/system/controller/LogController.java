package com.uloveits.wx.system.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;

import com.uloveits.wx.system.model.ActionLog;
import com.uloveits.wx.system.model.LoginLog;

import com.uloveits.wx.system.service.ActionLogService;
import com.uloveits.wx.system.service.LoginLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Map;

/**
 * @author lyrics
 */
@Api(value = "日志管理", tags = "log")
@RestController
@RequestMapping("${api.version}/log")
public class LogController extends BaseController {
    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private LoginLogService loginLogService;

    @RequiresPermissions("post:/v1/log/action/all")
    @ApiOperation(value = "查看所有操作日志")
    @PostMapping("/action/all")
    public  PageResult<ActionLog> actionList(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/log/action/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return actionLogService.getList(param);
        }else {
            return actionLogService.getPage(param);
        }
    }

    @RequiresPermissions("post:/v1/log/action/delete")
    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/action/delete")
    public JsonResult actionDelete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/log/action/delete",param.toString());

        if (actionLogService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }


    @RequiresPermissions("post:/v1/log/login/all")
    @ApiOperation(value = "查看所有登陆日志")
    @PostMapping("/login/all")
    public  PageResult<LoginLog> loginList(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/log/login/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return loginLogService.getList(param);
        }else {
            return loginLogService.getPage(param);
        }
    }

    @RequiresPermissions("post:/v1/log/login/delete")
    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/login/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/log/login/delete",param.toString());

        if (loginLogService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }
    }


}
