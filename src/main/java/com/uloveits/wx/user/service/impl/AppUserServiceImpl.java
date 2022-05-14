package com.uloveits.wx.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.user.dao.AppUserMapper;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;
import com.uloveits.wx.user.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    @Override
    public PageResult<AppUser> getPage(Map<String, Object> param) {

        //获得分页参数
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<AppUser> appUser = new Page<>(pageParam.get("page"), pageParam.get("limit"));

        EntityWrapper<AppUser> wrapper = new EntityWrapper<>();

        // 模糊筛选昵称
        if (param.get("nickName") != null && StringUtil.isNotBlank(param.get("nickName").toString())) {
            wrapper.like("nick_name", param.get("nickName").toString());
        }
        wrapper.orderBy("create_time", true);

        super.selectPage(appUser,wrapper);

        List<AppUser> list = appUser.getRecords();

        return new PageResult<>(list, appUser.getTotal());
    }

    @Override
    public PageResult<AppUser> getList(Map<String, Object> param) {

        EntityWrapper<AppUser> wrapper = new EntityWrapper<>();

        if (param.get("nickName") != null && StringUtil.isNotBlank(param.get("nickName").toString())) {
            wrapper.eq("nick_name", Integer.parseInt(param.get("nickName").toString()));
        }

        wrapper.orderBy("create_time", true);

        List<AppUser> list = super.selectList(wrapper);


        return new PageResult<>(list);
    }

    @Override
    public Boolean bind(Map<String, Object> param){
        String userId = param.get("userId").toString();
        String bindId = param.get("bindId").toString();
        //查找userId的信息
        AppUser user = super.selectById(userId);
        AppUser bindUser = super.selectById(bindId);
        if(user.getBindId() == null && bindUser.getBindId() == null) {
            //执行绑定操作
            user.setBindId(bindId);
            bindUser.setBindId(userId);
            if (super.insertOrUpdate(user) && super.insertOrUpdate(bindUser)) {
                return true;
            }
        }else {
            throw new BusinessException("你们之间已经有一个绑定了对象了,不能贪心哦");
        }

        return false;
    }

    @Override
    public AppUser detail(Map<String, Object> param){
        AppUser user = super.selectById(param.get("id").toString());
        if(user.getBindId() != null) {
            AppUser bindUser = super.selectById(user.getBindId());
            user.setBindUser(bindUser);
        }
        return user;
    }

}
