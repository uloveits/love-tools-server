package com.uloveits.wx.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.goods.model.Cart;

import java.util.Map;


/**
 * @author lyrics
 */
public interface CartService extends IService<Cart> {

    Boolean add(Cart cart);

    Boolean checked(Cart cart);

    Boolean checkedAll(Map<String, Object> param);

    PageResult<Cart> getPage(Map<String, Object> param);

    PageResult<Cart> getList(Map<String, Object> param);

}
