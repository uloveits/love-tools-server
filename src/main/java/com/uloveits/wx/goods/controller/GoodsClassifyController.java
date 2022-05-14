package com.uloveits.wx.goods.controller;

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
 * 商品分类管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "商品分类管理", tags = "setting")

@RequestMapping("${api.version.app}/goods")
@RestController
public class GoodsClassifyController extends BaseController {

    @Autowired
    private GoodsClassifyService goodsClassifyService;


    @ApiOperation(value = "添加")
    @PostMapping("/classify/add")
    public JsonResult add(HttpServletRequest request, @RequestBody GoodsClassify param) {
        super.insertActionLog(request,"post:/app/goods/classify/add",param.toString());

        GoodsClassify goodsClassify = param;
        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        goodsClassify.setUrlId(fileId);

        if(goodsClassifyService.add(goodsClassify)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "修改")
    @PostMapping("/classify/update")
    public JsonResult update(HttpServletRequest request,@RequestBody GoodsClassify param) {
        super.insertActionLog(request,"post:/app/goods/classify/update",param.toString());

        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(goodsClassifyService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/classify/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/goods/classify/delete",param.toString());

        if (goodsClassifyService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/classify/all")
    public PageResult<GoodsClassify> list(HttpServletRequest request,@RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/goods/classify/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return goodsClassifyService.getList(param);
        }else {
            return goodsClassifyService.getPage(param);
        }
    }

    @ApiOperation(value = "查看所有(tree)")
    @PostMapping("/classify/all/tree")
    public JsonResult tree(HttpServletRequest request,@RequestBody Map<String, Object> param){
        super.insertActionLog(request,"post:/app/goods/classify/all/tree",param.toString());

        return JsonResult.ok("操作成功").put("data",goodsClassifyService.getTreeList(param)) ;
    }

    @ApiOperation(value = "查看所有(tree)")
    @PostMapping("/classify/select/tree")
    public JsonResult selectTree(HttpServletRequest request,@RequestBody Map<String, Object> param){
        super.insertActionLog(request,"post:/app/goods/classify/select/tree",param.toString());

        return JsonResult.ok("操作成功").put("data",goodsClassifyService.getSelectTree(param)) ;
    }


}
