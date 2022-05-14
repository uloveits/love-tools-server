package com.uloveits.wx.column.controller;

import com.uloveits.wx.column.model.ColumnCashGift;
import com.uloveits.wx.column.service.ColumnCashGiftService;
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
 * 栏目礼金管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "栏目礼金管理", tags = "column")

@RequestMapping("${api.version.app}/column")
@RestController
public class ColumnCashGiftController extends BaseController {

    @Autowired
    private ColumnCashGiftService columnCashGiftService;


    @ApiOperation(value = "添加或修改")
    @PostMapping("/cashGift/update")
    public JsonResult update(HttpServletRequest request,@RequestBody ColumnCashGift param) {
        super.insertActionLog(request,"post:/app/column/cashGift/update",param.toString());

        if(columnCashGiftService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/cashGift/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/cashGift/delete",param.toString());

        if (columnCashGiftService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "详情", notes = "")
    @PostMapping("/cashGift/detail")
    public JsonResult detail(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/column/cashGift/detail",param.toString());

        ColumnCashGift detail = columnCashGiftService.selectById(param.get("id").toString());
        return JsonResult.ok("删除成功").put("data",detail);

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/cashGift/all")
    public PageResult<ColumnCashGift> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/column/cashGift/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return columnCashGiftService.getList(param);
        }else {
            return columnCashGiftService.getPage(param);
        }
    }

}
