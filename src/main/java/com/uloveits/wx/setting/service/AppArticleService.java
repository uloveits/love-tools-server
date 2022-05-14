package com.uloveits.wx.setting.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.setting.model.AppArticle;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyrics
 */
public interface AppArticleService extends IService<AppArticle> {

    Boolean add(AppArticle appArticle);

    Boolean update(AppArticle appArticle);

    PageResult<AppArticle> getPage(Map<String, Object> param) throws UnknownHostException;

    PageResult<AppArticle> getList(Map<String, Object> param);
}
