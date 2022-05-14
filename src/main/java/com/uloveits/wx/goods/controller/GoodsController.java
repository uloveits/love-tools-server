package com.uloveits.wx.goods.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.goods.model.Goods;
import com.uloveits.wx.goods.model.GoodsSku;
import com.uloveits.wx.goods.service.GoodsService;
import com.uloveits.wx.goods.service.GoodsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.net.UnknownHostException;
import java.util.Map;


/**
 * 商品管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "商品管理", tags = "setting")

@RequestMapping("${api.version.app}/goods")
@RestController
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;



    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request, @RequestBody Goods param) {
        super.insertActionLog(request,"post:/app/goods/add",param.toString());

        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(goodsService.add(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public JsonResult update(HttpServletRequest request, @RequestBody Goods param) {
        super.insertActionLog(request,"post:/app/goods/update",param.toString());

        //将fileId通过字符串方式储存到数据库
        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(goodsService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/goods/delete",param.toString());

        if (goodsService.deleteById(param.get("id").toString())) {
            Boolean isDelete = goodsSkuService.delete(new EntityWrapper<GoodsSku>().eq("goods_id",param.get("id").toString()));
            if(isDelete) {
                return JsonResult.ok("删除成功");
            }
            return JsonResult.error("删除失败");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有")
    @PostMapping("/all")
    public PageResult<Goods> list(HttpServletRequest request, @RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/goods/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return goodsService.getList(param);
        }else {
            return goodsService.getPage(param);
        }
    }

    @ApiOperation(value = "详情")
    @PostMapping("/detail")
    public JsonResult detail(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/goods/detail",param.toString());

        Goods _goods =  goodsService.detail(param.get("id").toString());

        return JsonResult.ok("操作成功").put("data",_goods);
    }


}
