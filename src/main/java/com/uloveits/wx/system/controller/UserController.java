package com.uloveits.wx.system.controller;


import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;

import com.uloveits.wx.system.model.User;

import com.uloveits.wx.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Map;

@Api(value = "用户管理", tags = "user")
@RestController
@RequestMapping("${api.version}/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @RequiresPermissions("post:/v1/user/all")
    @ApiOperation(value = "查询所有用户", notes = "")
    @PostMapping("/all")
    public PageResult<User> list(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/user/all",param.toString());

         return userService.getData(param);
    }


    @RequiresPermissions("post:/v1/user/add")
    @ApiOperation(value = "添加用户", notes = "")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request,@RequestBody User param) {
        super.insertActionLog(request,"post:/v1/user/add",param.toString());

        if(userService.add(param)){
            return JsonResult.ok("添加成功");
        }else {
            return JsonResult.ok("添加失败");
        }
    }

    @RequiresPermissions("post:/v1/user/update")
    @ApiOperation(value = "修改用户", notes = "")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request,@RequestBody User param) {
        super.insertActionLog(request,"post:/v1/user/update",param.toString());

        if(userService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }


    @RequiresPermissions("post:/v1/user/psw")
    @ApiOperation(value = "修改自己密码", notes = "")
    @PostMapping("/psw")
    public JsonResult updatePsw(HttpServletRequest request,@RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/v1/user/psw",param.toString());

        if (!DigestUtils.md5DigestAsHex(param.get("oldPsw").toString().getBytes()).equals(userService.selectById(getLoginUserId(request)).getPassword())) {
            return JsonResult.error("原密码不正确");
        }
        User user = new User();
        user.setUserId(getLoginUserId(request));
        user.setPassword(DigestUtils.md5DigestAsHex(param.get("newPsw").toString().getBytes()));
        if (userService.updateById(user)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    @RequiresPermissions("post:/v1/user/psw/reset")
    @ApiOperation(value = "重置密码", notes = "")
    @PostMapping("/psw/reset")
    public JsonResult resetPsw(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/user/psw/reset",param.toString());

        User user = new User();
        user.setUserId(Integer.parseInt(param.get("userId").toString()));
        user.setPassword(DigestUtils.md5DigestAsHex("888888".getBytes()));
        if (userService.updateById(user)) {
            return JsonResult.ok("重置密码成功");
        }
        return JsonResult.error("重置密码失败");
    }

    @RequiresPermissions("post:/v1/user/delete")
    @ApiOperation(value = "删除用户", notes = "")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/v1/user/delete",param.toString());

        if (userService.deleteById(Integer.parseInt(param.get("userId").toString()))) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }
}
