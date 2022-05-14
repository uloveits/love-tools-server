package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnRememberSubscribeMapper;
import com.uloveits.wx.column.model.ColumnRememberSubscribe;
import com.uloveits.wx.column.service.ColumnRememberSubscribeService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class ColumnRememberSubscribeServiceImpl extends ServiceImpl<ColumnRememberSubscribeMapper, ColumnRememberSubscribe> implements ColumnRememberSubscribeService {


    @Override
    public Boolean update(Map<String, Object> param){
        String type = param.get("type").toString();
        String rememberId = param.get("rememberId").toString();
        String userId = param.get("userId").toString();

        //先通过rememberId查看是否有数据
        ColumnRememberSubscribe columnRememberSubscribe = super.selectOne(new EntityWrapper().eq("remember_id", rememberId).eq("user_id",userId));
        if(columnRememberSubscribe != null){
            Integer newCount = "plus".equals(type) ? columnRememberSubscribe.getCount() + 1 : columnRememberSubscribe.getCount() - 1;
            columnRememberSubscribe.setCount(newCount);
            if (super.insertOrUpdate(columnRememberSubscribe)) {
                return true;
            }
        }else {
            ColumnRememberSubscribe _columnRememberSubscribe = new ColumnRememberSubscribe();
            _columnRememberSubscribe.setRememberId(rememberId);
            _columnRememberSubscribe.setUserId(userId);
            if (super.insertOrUpdate(_columnRememberSubscribe)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public PageResult<ColumnRememberSubscribe> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnRememberSubscribe> columnRememberSubscribe = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<ColumnRememberSubscribe> wrapper = new EntityWrapper<>();


        wrapper.orderBy("create_time", true);

        super.selectPage(columnRememberSubscribe,wrapper);

        List<ColumnRememberSubscribe> list = columnRememberSubscribe.getRecords();

        return new PageResult<>(list, columnRememberSubscribe.getTotal());
    }

    @Override
    public PageResult<ColumnRememberSubscribe> getList(Map<String, Object> param) {

        EntityWrapper<ColumnRememberSubscribe> wrapper = new EntityWrapper<>();

        wrapper.orderBy("create_time", true);

        List<ColumnRememberSubscribe> list = super.selectList(wrapper);

        return new PageResult<>(list);
    }

}
