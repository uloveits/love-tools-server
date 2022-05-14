package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.system.dao.LoginLogMapper;
import com.uloveits.wx.system.model.LoginLog;
import com.uloveits.wx.system.service.LoginLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    public PageResult<LoginLog> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<LoginLog> loginLog = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<LoginLog> wrapper = new EntityWrapper<>();

        wrapper.orderBy("create_time", false);

        super.selectPage(loginLog,wrapper);

        List<LoginLog> list = loginLog.getRecords();

        return new PageResult<>(list, loginLog.getTotal());
    }

    @Override
    public PageResult<LoginLog> getList(Map<String, Object> param) {

        List<LoginLog> list = super.selectList(new EntityWrapper<LoginLog>().orderBy("create_time", false));

        return new PageResult<>(list);
    }
}
