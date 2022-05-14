package com.uloveits.wx.setting.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.setting.dao.AppArticleMapper;

import com.uloveits.wx.setting.model.AppArticle;
import com.uloveits.wx.setting.service.AppArticleService;

import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class AppArticleServiceImpl extends ServiceImpl<AppArticleMapper, AppArticle> implements AppArticleService {

    @Autowired
    private FileService fileService;

    @Override
    public Boolean add(AppArticle appArticle) {
        if(super.insert(appArticle)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(AppArticle appArticle){
        if (super.updateById(appArticle)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<AppArticle> getPage(Map<String, Object> param) {
        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<AppArticle> appArticle = new Page<>(page, limit);
        EntityWrapper<AppArticle> wrapper = new EntityWrapper<>();

        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }

        wrapper.orderBy("sort", true);

        super.selectPage(appArticle,wrapper);

        List<AppArticle> appArticleList = appArticle.getRecords();



        return new PageResult<>(getFileList(appArticleList), appArticle.getTotal());
    }

    @Override
    public PageResult<AppArticle> getList(Map<String, Object> param) {

        EntityWrapper<AppArticle> wrapper = new EntityWrapper<>();

        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }

        wrapper.orderBy("sort", true);

        List<AppArticle> list = super.selectList(wrapper);

        return new PageResult<>(getFileList(list));
    }

    /**
     * 通过urlId获取文件列表
     * @param list
     * @return
     */
    public List<AppArticle> getFileList(List<AppArticle> list ) {
        for(AppArticle _appArticle : list) {
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_appArticle.getUrlId());
            if(_fileIds.size()>0){
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _appArticle.setFileList(_fileList);
        }
        return list;
    }

}
