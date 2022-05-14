package com.uloveits.wx.goods.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.goods.model.SkuClassify;
import com.uloveits.wx.goods.service.SkuClassifyService;
import com.uloveits.wx.goods.service.SkuService;
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
 * 商品分类管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "商品规格管理", tags = "setting")

@RequestMapping("${api.version.app}/sku")
@RestController
public class SkuController extends BaseController {

    @Autowired
    private SkuService skuService;


    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request, @RequestBody Sku param) {
        super.insertActionLog(request,"post:/app/sku/add",param.toString());

        if(skuService.add(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request, @RequestBody Sku param) {
        super.insertActionLog(request,"post:/app/sku/update",param.toString());

        if(skuService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/sku/delete",param.toString());

        if (skuService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/all")
    public PageResult<Sku> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/sku/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return skuService.getList(param);
        }else {
            return skuService.getPage(param);
        }
    }

}
