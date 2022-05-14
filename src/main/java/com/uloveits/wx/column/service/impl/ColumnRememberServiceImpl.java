package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnRememberMapper;
import com.uloveits.wx.column.model.ColumnRemember;
import com.uloveits.wx.column.model.ColumnRememberSubscribe;
import com.uloveits.wx.column.service.ColumnRememberService;
import com.uloveits.wx.column.service.ColumnRememberSubscribeService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.support.message.SubscribeMessageUtils;
import com.uloveits.wx.common.support.message.model.TemplateData;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.LunarCalendarUtil;
import com.uloveits.wx.common.utils.StringUtil;
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
public class ColumnRememberServiceImpl extends ServiceImpl<ColumnRememberMapper, ColumnRemember> implements ColumnRememberService {

    @Autowired
    private ColumnRememberSubscribeService columnRememberSubscribeService;

    @Autowired
    private AppUserService appUserService;


    @Override
    public Boolean update(ColumnRemember columnRemember){
        if (super.insertOrUpdate(columnRemember)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<ColumnRemember> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnRemember> columnRemember = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<ColumnRemember> wrapper = new EntityWrapper<>();

        // type筛选
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type", param.get("type").toString());
        }else {
            wrapper.eq("type", "0");
        }

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

        super.selectPage(columnRemember,wrapper);

        List<ColumnRemember> list = columnRemember.getRecords();

        return new PageResult<>(list, columnRemember.getTotal());
    }

    @Override
    public PageResult<ColumnRemember> getList(Map<String, Object> param) {

        EntityWrapper<ColumnRemember> wrapper = new EntityWrapper<>();

        // type筛选
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type", param.get("type").toString());
        }else {
            wrapper.eq("type", "0");
        }

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

        List<ColumnRemember> list = super.selectList(wrapper);

        return new PageResult<>(list);
    }


    /**
     * 发送订阅消息
     */
    @Override
    public void sendTemplateMessage() {
        //取得所有没有发送过提醒的订单
        List<ColumnRememberSubscribe> list = columnRememberSubscribeService.selectList( new EntityWrapper<>());
        for(ColumnRememberSubscribe subscribe : list) {
            if(subscribe.getCount() > 0){
                //找出纪念日数据
                ColumnRemember remember = super.selectById(subscribe.getRememberId());
                if(remember != null) {
                    //比较是否到期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String nowDate = sdf.format(new Date());
                    Date endTime = null;
                    Date now = null;
                    try {
                        now = sdf.parse(nowDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        if("1".equals(remember.getIsLunar())){
                            endTime = sdf.parse(remember.getLunarTimeNum());
                            String _endTime = sdf.format(endTime);
                            String _nowTime = sdf.format(now);
                            String[] splitEndTime=_endTime.split("-");
                            String[] splitNowTime=_nowTime.split("-");
                            int[] solarTime = LunarCalendarUtil.lunarToSolar(Integer.parseInt(splitNowTime[0]),Integer.parseInt(splitEndTime[1]),Integer.parseInt(splitEndTime[2]),false);
                            endTime = sdf.parse(Integer.toString(solarTime[0]) +"-"+Integer.toString(solarTime[1])+"-"+Integer.toString(solarTime[2]) );
                        }else {
                            endTime = sdf.parse(remember.getTime());
                            String _endTime = sdf.format(endTime);
                            String _nowTime = sdf.format(now);
                            String[] splitEndTime=_endTime.split("-");
                            String[] splitNowTime=_nowTime.split("-");
                            endTime = sdf.parse(splitNowTime[0]+"-"+splitEndTime[1]+"-"+splitEndTime[2]);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    System.out.println("endTime== : " + endTime);
                    System.out.println("now== : " + now);

                    System.out.println("endTime : " + sdf.format(endTime));
                    System.out.println("now : " + sdf.format(now));

                    if (endTime.equals(now)) {
                        System.out.println("当天");
                        Map<String, TemplateData> map = new HashMap<>();
                        map.put("thing1", new TemplateData(remember.getName()));
                        map.put("time2", new TemplateData(sdf.format(endTime)));
                        map.put("thing4", new TemplateData("期待您有个愉快的纪念日~"));
                        //获取用户信息
                        AppUser user = appUserService.selectById(subscribe.getUserId());
                        String appId = user.getOpenId();
                        String templateId = Resources.THIRDPARTY.getString("templateId");
                        String page = "/pages/column/remember/index";
                        //发送模板消息
                        SubscribeMessageUtils.sendTemplateMessage(appId,templateId,page,map);
                        //减少次数
                        Map<String,Object> param = new HashMap<>();
                        param.put("type","reduce");
                        param.put("rememberId",subscribe.getRememberId());
                        param.put("userId",subscribe.getUserId());
                        columnRememberSubscribeService.update(param);
                    }
                }
            }
        }

    }

}
