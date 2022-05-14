package com.uloveits.wx.setting.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.setting.dao.AppContactMapper;
import com.uloveits.wx.setting.model.AppContact;
import com.uloveits.wx.setting.service.AppContactService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


/**
 * Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class AppContactServiceImpl extends ServiceImpl<AppContactMapper, AppContact> implements AppContactService {


    @Override
    public Boolean add(AppContact appContact) {
        if(super.insert(appContact)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(AppContact appContact){
        if (super.updateById(appContact)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<AppContact> getPage(Map<String, Object> param) {

        //获得分页参数
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<AppContact> appContact = new Page<>(pageParam.get("page"), pageParam.get("limit"));

        EntityWrapper<AppContact> wrapper = new EntityWrapper<>();


        wrapper.orderBy("create_time", true);

        super.selectPage(appContact,wrapper);

        List<AppContact> appNavigateList = appContact.getRecords();

        return new PageResult<>(appNavigateList, appContact.getTotal());
    }

    @Override
    public PageResult<AppContact> getList(Map<String, Object> param) {

        EntityWrapper<AppContact> wrapper = new EntityWrapper<>();


        wrapper.orderBy("create_time", true);

        List<AppContact> list = super.selectList(wrapper);


        return new PageResult<>(list);
    }


}
