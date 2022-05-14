package com.uloveits.wx.setting.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.setting.model.AppContact;
import java.util.Map;

/**
 * @author lyrics
 */
public interface AppContactService extends IService<AppContact> {

    Boolean add(AppContact appContact);

    Boolean update(AppContact appContact);

    PageResult<AppContact> getPage(Map<String, Object> param);

    PageResult<AppContact> getList(Map<String, Object> param);
}
