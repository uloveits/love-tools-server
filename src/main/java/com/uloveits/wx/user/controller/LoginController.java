package com.uloveits.wx.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.support.login.ThirdLoginHelper;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;

import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 用户登录
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "用户登陆", tags = "login")
@RequestMapping("${api.version.app}/")
@Controller
public class LoginController  extends BaseController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @ApiOperation(value = "微信用户登陆")
    @PostMapping("/login/wx")
    public JsonResult login(HttpServletRequest request, @RequestBody AppUser param) {
        super.insertActionLog(request,"post:/app/login/wx",param.toString());

        AppUser user = param;
        String code = user.getCode();
        if(StringUtil.isNotBlank(code)) {
            Map<String, String> map = ThirdLoginHelper.getWxTokenAndOpenid(code);
            String openId = map.get("openId");

            //如果openId存在
            if(StringUtil.isNotBlank(openId)) {
                //先判断数据库有没有该用户信息
                List<AppUser> list =loginService.selectList(new EntityWrapper().eq("open_id", openId));
                if(list.size() > 0){
                    //更新
                    user.setId(list.get(0).getId());
                    user.setToken(map.get("access_token"));
                    if(loginService.updateById(user)) {
                        return JsonResult.ok("登陆成功").put("data",user);
                    }
                }else {
                    //插入
                    user.setOpenId(openId);
                    user.setToken(map.get("access_token"));
                    if(loginService.insert(user)) {
                        return JsonResult.ok("登陆成功").put("data",user);
                    }

                }
            }

        }

        return JsonResult.error("登陆失败");

    }

}
