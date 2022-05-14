package com.uloveits.wx.column.controller;

import com.uloveits.wx.column.model.ColumnRemember;
import com.uloveits.wx.column.model.ColumnRememberSubscribe;
import com.uloveits.wx.column.service.ColumnRememberService;
import com.uloveits.wx.column.service.ColumnRememberSubscribeService;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
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
 * 栏目纪念日管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "栏目纪念日管理", tags = "column")

@RequestMapping("${api.version.app}/column")
@RestController
public class ColumnRememberController extends BaseController {

    @Autowired
    private ColumnRememberService columnRememberService;

    @Autowired
    private ColumnRememberSubscribeService columnRememberSubscribeService;


    @ApiOperation(value = "添加或修改")
    @PostMapping("/remember/update")
    public JsonResult update(HttpServletRequest request,@RequestBody ColumnRemember param) {
        super.insertActionLog(request,"post:/app/column/remember/update",param.toString());

        if(columnRememberService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/remember/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/remember/delete",param.toString());

        if (columnRememberService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "详情", notes = "")
    @PostMapping("/remember/detail")
    public JsonResult detail(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/detail/delete",param.toString());

        ColumnRemember detail = columnRememberService.selectById(param.get("id").toString());
        return JsonResult.ok("删除成功").put("data",detail);

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/remember/all")
    public PageResult<ColumnRemember> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/column/remember/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return columnRememberService.getList(param);
        }else {
            return columnRememberService.getPage(param);
        }
    }


    @ApiOperation(value = "添加或修改")
    @PostMapping("/remember/subscribe/update")
    public JsonResult subscribeUpdate(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/remember/subscribe/update",param.toString());

        if(columnRememberSubscribeService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

}
