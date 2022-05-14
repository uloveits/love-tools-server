package com.uloveits.wx.goods.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;

import com.uloveits.wx.goods.model.SkuClassify;

import com.uloveits.wx.goods.service.SkuClassifyService;
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
 * 商品规格分类管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "商品规格分类管理", tags = "setting")

@RequestMapping("${api.version.app}/sku")
@RestController
public class SkuClassifyController extends BaseController {

    @Autowired
    private SkuClassifyService skuClassifyService;


    @ApiOperation(value = "添加")
    @PostMapping("/classify/add")
    public JsonResult add(HttpServletRequest request, @RequestBody SkuClassify param) {
        super.insertActionLog(request,"post:/app/sku/classify/add",param.toString());

        if(skuClassifyService.add(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "修改")
    @PostMapping("/classify/update")
    public JsonResult update(HttpServletRequest request, @RequestBody SkuClassify param) {
        super.insertActionLog(request,"post:/app/sku/classify/update",param.toString());

        if(skuClassifyService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/classify/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/sku/classify/delete",param.toString());

        if (skuClassifyService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/classify/all")
    public PageResult<SkuClassify> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/sku/classify/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return skuClassifyService.getList(param);
        }else {
            return skuClassifyService.getPage(param);
        }
    }

}
