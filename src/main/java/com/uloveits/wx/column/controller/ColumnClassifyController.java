package com.uloveits.wx.column.controller;

import com.uloveits.wx.column.model.ColumnClassify;
import com.uloveits.wx.column.service.ColumnClassifyService;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.service.GoodsClassifyService;
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
 * 栏目分类管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "栏目分类管理", tags = "column")

@RequestMapping("${api.version.app}/column")
@RestController
public class ColumnClassifyController extends BaseController {

    @Autowired
    private ColumnClassifyService columnClassifyService;


    @ApiOperation(value = "添加或修改")
    @PostMapping("/classify/update")
    public JsonResult update(HttpServletRequest request,@RequestBody ColumnClassify param) {
        super.insertActionLog(request,"post:/app/column/classify/update",param.toString());

        if(columnClassifyService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/classify/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/classify/delete",param.toString());

        if (columnClassifyService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/classify/all")
    public PageResult<ColumnClassify> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/column/classify/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return columnClassifyService.getList(param);
        }else {
            return columnClassifyService.getPage(param);
        }
    }

}
