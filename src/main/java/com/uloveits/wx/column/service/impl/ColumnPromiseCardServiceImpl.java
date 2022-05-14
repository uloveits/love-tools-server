package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnPromiseCardMapper;
import com.uloveits.wx.column.model.ColumnPromiseCard;
import com.uloveits.wx.column.service.ColumnPromiseCardService;
import com.uloveits.wx.common.PageResult;
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
public class ColumnPromiseCardServiceImpl extends ServiceImpl<ColumnPromiseCardMapper, ColumnPromiseCard> implements ColumnPromiseCardService {


    @Autowired
    private AppUserService appUserService;


    @Override
    public Boolean update(ColumnPromiseCard columnPromiseCard){
        if (super.insertOrUpdate(columnPromiseCard)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<ColumnPromiseCard> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnPromiseCard> columnPromiseCard = new Page<>(pageParam.get("page"), pageParam.get("limit"));

        EntityWrapper<ColumnPromiseCard> wrapper = new EntityWrapper<>();

        if (param.get("createId") != null && StringUtil.isNotBlank(param.get("createId").toString())) {
            // createId筛选
            wrapper.eq("create_id",param.get("createId").toString());
        }

        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            // userId筛选
            wrapper.eq("user_id",param.get("userId").toString());
        }

        if (param.get("status") != null && StringUtil.isNotBlank(param.get("status").toString())) {
            // type筛选
            wrapper.eq("status",param.get("status").toString());
        }

        wrapper.orderBy("create_time", true);

        super.selectPage(columnPromiseCard,wrapper);

        List<ColumnPromiseCard> list = columnPromiseCard.getRecords();

        for(ColumnPromiseCard promiseCard : list) {
            aggregation(promiseCard);
        }

        return new PageResult<>(list, columnPromiseCard.getTotal());
    }

    @Override
    public PageResult<ColumnPromiseCard> getList(Map<String, Object> param) {

        EntityWrapper<ColumnPromiseCard> wrapper = new EntityWrapper<>();

        if (param.get("createId") != null && StringUtil.isNotBlank(param.get("createId").toString())) {
            // createId筛选
            wrapper.eq("create_id",param.get("createId").toString());
        }

        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            // userId筛选
            wrapper.eq("user_id",param.get("userId").toString());
        }

        if (param.get("status") != null && StringUtil.isNotBlank(param.get("status").toString())) {
            // type筛选
            wrapper.eq("status",param.get("status").toString());
        }

        wrapper.orderBy("create_time", true);

        List<ColumnPromiseCard> list = super.selectList(wrapper);

        for(ColumnPromiseCard promiseCard : list) {
            aggregation(promiseCard);
        }

        return new PageResult<>(list);
    }

    /**
     * 聚合数据
     * @param promiseCard
     * @return
     */
    private ColumnPromiseCard aggregation(ColumnPromiseCard promiseCard) {
        Map<String, Object> param = new HashMap<>();
        param.put("id",promiseCard.getCreateId());
        AppUser user = appUserService.detail(param);
        promiseCard.setCreateInfo(user);
        return promiseCard;
    }


}
