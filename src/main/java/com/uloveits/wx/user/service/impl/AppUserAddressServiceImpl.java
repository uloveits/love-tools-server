package com.uloveits.wx.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.model.Sku;
import com.uloveits.wx.user.dao.AppUserAddressMapper;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;
import com.uloveits.wx.user.service.AppUserAddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class AppUserAddressServiceImpl extends ServiceImpl<AppUserAddressMapper, AppUserAddress> implements AppUserAddressService {

    @Override
    public Boolean update(AppUserAddress appUserAddress){
        if (super.insertOrUpdate(appUserAddress)) {
            return true;
        }
        return false;
    }

    @Override
    public PageResult<AppUserAddress> getPage(Map<String, Object> param) {

        //获得分页参数
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<AppUserAddress> appUserAddress = new Page<>(pageParam.get("page"), pageParam.get("limit"));

        EntityWrapper<AppUserAddress> wrapper = new EntityWrapper<>();
        // 模糊筛选昵称
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.eq("user_id", param.get("userId").toString());
        }
        wrapper.orderBy("create_time", true);

        super.selectPage(appUserAddress,wrapper);

        List<AppUserAddress> list = appUserAddress.getRecords();

        return new PageResult<>(list, appUserAddress.getTotal());
    }

    @Override
    public PageResult<AppUserAddress> getList(Map<String, Object> param) {

        EntityWrapper<AppUserAddress> wrapper = new EntityWrapper<>();
        // 模糊筛选昵称
        if (param.get("userId") != null && StringUtil.isNotBlank(param.get("userId").toString())) {
            wrapper.eq("user_id", param.get("userId").toString());
        }
        wrapper.orderBy("create_time", true);

        List<AppUserAddress> list = super.selectList(wrapper);


        return new PageResult<>(list);
    }

}
