package com.uloveits.wx.user.controller;


import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


/**
 * 小程序用户列表
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "小程序用户管理", tags = "appUser")
@RequestMapping("${api.version.app}/user")
@RestController
public class AppUserController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "查看所有")
    @PostMapping("/all")
    public PageResult<AppUser> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appUserService.getList(param);
        }else {
            return appUserService.getPage(param);
        }
    }

    @ApiOperation(value = "绑定对象")
    @PostMapping("/bind")
    public JsonResult bind(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/bind",param.toString());
        if(appUserService.bind(param)){
            return JsonResult.ok("绑定成功");
        }
        return JsonResult.error("绑定失败");
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/detail")
    public JsonResult detail(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/info",param.toString());
        AppUser user = appUserService.detail(param);
        return JsonResult.ok("获取成功").put("data",user);
    }

}
