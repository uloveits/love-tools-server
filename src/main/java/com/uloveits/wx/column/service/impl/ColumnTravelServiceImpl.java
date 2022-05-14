package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnTravelMapper;
import com.uloveits.wx.column.model.ColumnRemember;
import com.uloveits.wx.column.model.ColumnRememberSubscribe;
import com.uloveits.wx.column.model.ColumnTravel;
import com.uloveits.wx.column.service.ColumnRememberSubscribeService;
import com.uloveits.wx.column.service.ColumnTravelService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.support.message.SubscribeMessageUtils;
import com.uloveits.wx.common.support.message.model.TemplateData;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.LunarCalendarUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.model.Cart;
import com.uloveits.wx.goods.model.Goods;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class ColumnTravelServiceImpl extends ServiceImpl<ColumnTravelMapper, ColumnTravel> implements ColumnTravelService {


    @Autowired
    private AppUserService appUserService;


    @Override
    public Boolean update(ColumnTravel columnTravel){
        if (super.insertOrUpdate(columnTravel)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<ColumnTravel> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnTravel> columnTravel = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<ColumnTravel> wrapper = new EntityWrapper<>();

        if (param.get("id") != null && StringUtil.isNotBlank(param.get("id").toString()) && param.get("bindId") != null && StringUtil.isNotBlank(param.get("bindId").toString())) {
            // 通过userId或bindId筛选
            Object[] object = new Object[2];
            object [0] = param.get("id").toString();
            object [1] = param.get("bindId").toString();
            wrapper.in("create_id",object);
        } else {
            throw new BusinessException("参数错误");
        }


        wrapper.orderBy("create_time", true);

        super.selectPage(columnTravel,wrapper);

        List<ColumnTravel> list = columnTravel.getRecords();

        for(ColumnTravel travel : list) {
            aggregation(travel);
        }

        return new PageResult<>(list, columnTravel.getTotal());
    }

    @Override
    public PageResult<ColumnTravel> getList(Map<String, Object> param) {

        EntityWrapper<ColumnTravel> wrapper = new EntityWrapper<>();

        if (param.get("id") != null && StringUtil.isNotBlank(param.get("id").toString()) && param.get("bindId") != null && StringUtil.isNotBlank(param.get("bindId").toString())) {
            // 通过userId或bindId筛选
            Object[] object = new Object[2];
            object [0] = param.get("id").toString();
            object [1] = param.get("bindId").toString();
            wrapper.in("create_id",object);
        } else {
            throw new BusinessException("参数错误");
        }

        wrapper.orderBy("create_time", true);

        List<ColumnTravel> list = super.selectList(wrapper);

        for(ColumnTravel travel : list) {
            aggregation(travel);
        }

        return new PageResult<>(list);
    }

    /**
     * 聚合数据
     * @param travel
     * @return
     */
    private ColumnTravel aggregation(ColumnTravel travel) {
        Map<String, Object> param = new HashMap<>();
        param.put("id",travel.getCreateId());
        AppUser user = appUserService.detail(param);
        travel.setCreateInfo(user);
        return travel;
    }


}
