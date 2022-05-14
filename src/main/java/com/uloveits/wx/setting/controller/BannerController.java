package com.uloveits.wx.setting.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;

import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.setting.model.AppBanner;
import com.uloveits.wx.setting.service.AppBannerService;

import com.uloveits.wx.upload.model.File;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


/**
 * Banner 管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "Banner 管理", tags = "setting")

@RequestMapping("${api.version.app}/setting")
@RestController
public class BannerController extends BaseController {

    @Autowired
    private AppBannerService appBannerService;


    @ApiOperation(value = "轮播图添加")
    @PostMapping("/banner/add")
    public JsonResult add(HttpServletRequest request, @RequestBody AppBanner param) {
        super.insertActionLog(request,"post:/app/setting/banner/add",param.toString());

        AppBanner appBanner = param;
        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        appBanner.setUrlId(fileId);

        if(appBannerService.add(appBanner)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "轮播图修改")
    @PostMapping("/banner/update")
    public JsonResult update(HttpServletRequest request,@RequestBody AppBanner param) {
        super.insertActionLog(request,"post:/app/setting/banner/update",param.toString());

        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(appBannerService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除轮播图", notes = "")
    @PostMapping("/banner/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/banner/delete",param.toString());

        if (appBannerService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有的banner信息")
    @PostMapping("/banner/all")
    public PageResult<AppBanner> list(HttpServletRequest request,@RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/setting/banner/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appBannerService.getList(param);
        }else {
            return appBannerService.getPage(param);
        }
    }

}
