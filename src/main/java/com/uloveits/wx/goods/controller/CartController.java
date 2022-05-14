package com.uloveits.wx.goods.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;

import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Cart;
import com.uloveits.wx.goods.model.GoodsSku;
import com.uloveits.wx.goods.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 购物车管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "购物车管理", tags = "goods")

@RequestMapping("${api.version.app}/cart")
@RestController
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request, @RequestBody Cart param) {
        super.insertActionLog(request,"post:/app/cart/add",param.toString());

        if(cartService.add(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "选中商品")
    @PostMapping("/check")
    public JsonResult checked(HttpServletRequest request, @RequestBody Cart param) {
        super.insertActionLog(request,"post:/app/cart/check",param.toString());

        if(cartService.checked(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "选中所有")
    @PostMapping("/checkAll")
    public JsonResult checkedAll(HttpServletRequest request, @RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/app/cart/checkAll",param.toString());

        if(cartService.checkedAll(param)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/cart/delete",param.toString());

        if (cartService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }


    @ApiOperation(value = "查看所有")
    @PostMapping("/all")
    public PageResult<Cart> list(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/cart/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return cartService.getList(param);
        }else {
            return cartService.getPage(param);
        }
    }

}
