package com.uloveits.wx.column.controller;

import com.uloveits.wx.column.model.ColumnTravel;
import com.uloveits.wx.column.service.ColumnTravelService;
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
 * 栏目旅游足迹管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "栏目旅游足迹管理", tags = "column")

@RequestMapping("${api.version.app}/column")
@RestController
public class ColumnTravelController extends BaseController {

    @Autowired
    private ColumnTravelService columnTravelService;


    @ApiOperation(value = "添加或修改")
    @PostMapping("/travel/update")
    public JsonResult update(HttpServletRequest request,@RequestBody ColumnTravel param) {
        super.insertActionLog(request,"post:/app/column/travel/update",param.toString());

        if(columnTravelService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/travel/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/travel/delete",param.toString());

        if (columnTravelService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "详情", notes = "")
    @PostMapping("/travel/detail")
    public JsonResult detail(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/travel/detail",param.toString());

        ColumnTravel detail = columnTravelService.selectById(param.get("id").toString());
        return JsonResult.ok("删除成功").put("data",detail);

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/travel/all")
    public PageResult<ColumnTravel> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/column/travel/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return columnTravelService.getList(param);
        }else {
            return columnTravelService.getPage(param);
        }
    }

}
