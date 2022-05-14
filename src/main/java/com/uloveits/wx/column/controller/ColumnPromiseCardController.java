package com.uloveits.wx.column.controller;

import com.uloveits.wx.column.model.ColumnCashGift;
import com.uloveits.wx.column.model.ColumnPromiseCard;
import com.uloveits.wx.column.service.ColumnCashGiftService;
import com.uloveits.wx.column.service.ColumnPromiseCardService;
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
 * 栏目承诺券管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "栏目承诺券管理", tags = "column")

@RequestMapping("${api.version.app}/column")
@RestController
public class ColumnPromiseCardController extends BaseController {

    @Autowired
    private ColumnPromiseCardService columnPromiseCardService;


    @ApiOperation(value = "添加或修改")
    @PostMapping("/promiseCard/update")
    public JsonResult update(HttpServletRequest request,@RequestBody ColumnPromiseCard param) {
        super.insertActionLog(request,"post:/app/column/promiseCard/update",param.toString());

        if(columnPromiseCardService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/promiseCard/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/promiseCard/delete",param.toString());

        if (columnPromiseCardService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "详情", notes = "")
    @PostMapping("/promiseCard/detail")
    public JsonResult detail(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/promiseCard/detail",param.toString());

        ColumnPromiseCard detail = columnPromiseCardService.selectById(param.get("id").toString());
        return JsonResult.ok("删除成功").put("data",detail);

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/promiseCard/all")
    public PageResult<ColumnPromiseCard> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/column/promiseCard/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return columnPromiseCardService.getList(param);
        }else {
            return columnPromiseCardService.getPage(param);
        }
    }

}
