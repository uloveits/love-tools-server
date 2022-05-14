package com.uloveits.wx.column.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.column.model.ColumnCashGift;
import com.uloveits.wx.common.PageResult;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface ColumnCashGiftService extends IService<ColumnCashGift> {


    Boolean update(ColumnCashGift columnCashGift);

    PageResult<ColumnCashGift> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<ColumnCashGift> getList(Map<String, Object> param);


}
