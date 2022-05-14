package com.uloveits.wx.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.system.dao.ActionLogMapper;
import com.uloveits.wx.system.model.ActionLog;
import com.uloveits.wx.system.service.ActionLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
@Service
public class ActionLogServiceImpl extends ServiceImpl<ActionLogMapper, ActionLog> implements ActionLogService {

    @Override
    public PageResult<ActionLog> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ActionLog> actionLog = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<ActionLog> wrapper = new EntityWrapper<>();

        wrapper.orderBy("create_time", false);

        super.selectPage(actionLog,wrapper);

        List<ActionLog> list = actionLog.getRecords();

        return new PageResult<>(list, actionLog.getTotal());
    }

    @Override
    public PageResult<ActionLog> getList(Map<String, Object> param) {

        List<ActionLog> list = super.selectList(new EntityWrapper<ActionLog>().orderBy("create_time", false));

        return new PageResult<>(list);
    }
}
