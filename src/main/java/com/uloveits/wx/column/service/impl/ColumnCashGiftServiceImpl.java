package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnCashGiftMapper;
import com.uloveits.wx.column.model.ColumnCashGift;
import com.uloveits.wx.column.model.ColumnTravel;
import com.uloveits.wx.column.service.ColumnCashGiftService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class ColumnCashGiftServiceImpl extends ServiceImpl<ColumnCashGiftMapper, ColumnCashGift> implements ColumnCashGiftService {


    @Autowired
    private AppUserService appUserService;


    @Override
    public Boolean update(ColumnCashGift columnCashGift){
        if (super.insertOrUpdate(columnCashGift)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<ColumnCashGift> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnCashGift> columnCashGift = new Page<>(pageParam.get("page"), pageParam.get("limit"));

        EntityWrapper<ColumnCashGift> wrapper = new EntityWrapper<>();

        if (param.get("id") != null && StringUtil.isNotBlank(param.get("id").toString()) && param.get("bindId") != null && StringUtil.isNotBlank(param.get("bindId").toString())) {
            // 通过userId或bindId筛选
            Object[] object = new Object[2];
            object [0] = param.get("id").toString();
            object [1] = param.get("bindId").toString();
            wrapper.in("create_id",object);
        } else {
            throw new BusinessException("参数错误");
        }

        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            // type筛选
            wrapper.eq("type",param.get("type").toString());
        }

        wrapper.orderBy("create_time", true);

        super.selectPage(columnCashGift,wrapper);

        List<ColumnCashGift> list = columnCashGift.getRecords();

        for(ColumnCashGift cashGift : list) {
            aggregation(cashGift);
        }

        return new PageResult<>(list, columnCashGift.getTotal());
    }

    @Override
    public PageResult<ColumnCashGift> getList(Map<String, Object> param) {

        EntityWrapper<ColumnCashGift> wrapper = new EntityWrapper<>();

        if (param.get("id") != null && StringUtil.isNotBlank(param.get("id").toString()) && param.get("bindId") != null && StringUtil.isNotBlank(param.get("bindId").toString())) {
            // 通过userId或bindId筛选
            Object[] object = new Object[2];
            object [0] = param.get("id").toString();
            object [1] = param.get("bindId").toString();
            wrapper.in("create_id",object);
        } else {
            throw new BusinessException("参数错误");
        }

        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            // type筛选
            wrapper.eq("type",param.get("type").toString());
        }

        wrapper.orderBy("create_time", true);

        List<ColumnCashGift> list = super.selectList(wrapper);

        for(ColumnCashGift cashGift : list) {
            aggregation(cashGift);
        }

        return new PageResult<>(list);
    }

    /**
     * 聚合数据
     * @param cashGift
     * @return
     */
    private ColumnCashGift aggregation(ColumnCashGift cashGift) {
        Map<String, Object> param = new HashMap<>();
        param.put("id",cashGift.getCreateId());
        AppUser user = appUserService.detail(param);
        cashGift.setCreateInfo(user);
        return cashGift;
    }


}
