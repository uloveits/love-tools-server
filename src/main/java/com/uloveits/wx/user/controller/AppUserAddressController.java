package com.uloveits.wx.user.controller;


import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;
import com.uloveits.wx.user.service.AppUserAddressService;
import com.uloveits.wx.user.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 小程序用户地址管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "小程序用户地址管理", tags = "appUserAddress")
@RequestMapping("${api.version.app}/user")
@RestController
public class AppUserAddressController extends BaseController {

    @Autowired
    private AppUserAddressService appUserAddressService;

    @ApiOperation(value = "添加/修改")
    @PostMapping("/address/update")
    public JsonResult update(HttpServletRequest request, @RequestBody AppUserAddress param) {
        super.insertActionLog(request,"post:/app/user/address/update",param.toString());

        if(appUserAddressService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.error("修改失败");
        }
    }
    @ApiOperation(value = "查看所有")
    @PostMapping("/address/all")
    public PageResult<AppUserAddress> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/address/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appUserAddressService.getList(param);
        }else {
            return appUserAddressService.getPage(param);
        }
    }

    @ApiOperation(value = "详情")
    @PostMapping("/address/detail")
    public JsonResult detail(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/address/detail",param.toString());

        AppUserAddress appUserAddress = appUserAddressService.selectById(param.get("id").toString());
        if(appUserAddress != null){
            return JsonResult.ok("操作成功").put("data",appUserAddress);
        }else {
            return JsonResult.error("操作失败");
        }
    }

    @ApiOperation(value = "删除")
    @PostMapping("/address/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/user/address/delete",param.toString());

        if(appUserAddressService.deleteById(param.get("id").toString())){
            return JsonResult.ok("操作成功");
        }else {
            return JsonResult.error("操作失败");
        }
    }
}
