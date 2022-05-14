package com.uloveits.wx.setting.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.setting.model.AppArticle;
import com.uloveits.wx.setting.model.AppContact;
import com.uloveits.wx.setting.service.AppArticleService;
import com.uloveits.wx.setting.service.AppContactService;
import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


/**
 *  联系我们信息
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "联系我们信息管理", tags = "setting")

@RequestMapping("${api.version.app}/setting")
@RestController
public class ContactController extends BaseController {

    @Autowired
    private AppContactService appContactService;



    @ApiOperation(value = "添加")
    @PostMapping("/contact/add")
    public JsonResult add(HttpServletRequest request, @RequestBody AppContact param) {
        super.insertActionLog(request,"post:/app/setting/contact/add",param.toString());

        if(appContactService.add(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "修改")
    @PostMapping("/contact/update")
    public JsonResult update(HttpServletRequest request,@RequestBody AppContact param) {
        super.insertActionLog(request,"post:/app/setting/contact/update",param.toString());

        if(appContactService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/contact/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/contact/delete",param.toString());

        if (appContactService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/contact/all")
    public PageResult<AppContact> list(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/contact/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appContactService.getList(param);
        }else {
            return appContactService.getPage(param);
        }
    }

    @ApiOperation(value = "根据Id获得文章详情")
    @PostMapping("/contact/detail")
    public JsonResult info (HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/contact/detail",param.toString());

        AppContact info = appContactService.selectById(param.get("id").toString());
        return JsonResult.ok("操作成功").put("data",info);
    }

}
