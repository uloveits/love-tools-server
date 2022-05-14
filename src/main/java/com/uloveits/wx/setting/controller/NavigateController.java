package com.uloveits.wx.setting.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.setting.model.AppNavigate;
import com.uloveits.wx.setting.service.AppNavigateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.Map;


/**
 * 首页导航管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "首页导航管理", tags = "setting")

@RequestMapping("${api.version.app}/setting")
@RestController
public class NavigateController extends BaseController {

    @Autowired
    private AppNavigateService appNavigateService;


    @ApiOperation(value = "首页导航添加")
    @PostMapping("/navigate/add")
    public JsonResult add(HttpServletRequest request, @RequestBody AppNavigate param) {
        super.insertActionLog(request,"post:/app/setting/navigate/add",param.toString());

        AppNavigate appNavigate = param;
        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        appNavigate.setUrlId(fileId);

        if(appNavigateService.add(appNavigate)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "首页导航修改")
    @PostMapping("/navigate/update")
    public JsonResult update(HttpServletRequest request, @RequestBody AppNavigate param) {
        super.insertActionLog(request,"post:/app/setting/navigate/update",param.toString());

        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(appNavigateService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除首页导航", notes = "")
    @PostMapping("/navigate/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/navigate/delete",param.toString());

        if (appNavigateService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有的首页导航信息")
    @PostMapping("/navigate/all")
    public PageResult<AppNavigate> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/setting/navigate/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appNavigateService.getList(param);
        }else {
            return appNavigateService.getPage(param);
        }
    }

}
