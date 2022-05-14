package com.uloveits.wx.setting.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.setting.dao.AppBannerMapper;
import com.uloveits.wx.setting.model.AppArticle;
import com.uloveits.wx.setting.model.AppBanner;
import com.uloveits.wx.setting.service.AppBannerService;
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
public class AppBannerServiceImpl extends ServiceImpl<AppBannerMapper, AppBanner> implements AppBannerService {

    @Autowired
    private FileService fileService;

    @Override
    public Boolean add(AppBanner appBanner) {
        if(super.insert(appBanner)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(AppBanner appBanner){
        if (super.updateById(appBanner)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<AppBanner> getPage(Map<String, Object> param) {
        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<AppBanner> appBanner = new Page<>(page, limit);
        EntityWrapper<AppBanner> wrapper = new EntityWrapper<>();

        // 模糊筛选种类
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type_", Integer.parseInt(param.get("type").toString()));
        }
        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }



        wrapper.orderBy("sort", true);

        super.selectPage(appBanner,wrapper);

        List<AppBanner> appBannerList = appBanner.getRecords();

        return new PageResult<>(getFileList(appBannerList), appBanner.getTotal());
    }

    @Override
    public PageResult<AppBanner> getList(Map<String, Object> param) {

        EntityWrapper<AppBanner> wrapper = new EntityWrapper<>();

        // 模糊筛选种类
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type_", Integer.parseInt(param.get("type").toString()));
        }
        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }



        wrapper.orderBy("sort", true);

        List<AppBanner> list = super.selectList(wrapper);


        return new PageResult<>(getFileList(list));
    }

    /**
     * 通过urlId获取文件列表
     * @param list
     * @return
     */
    public List<AppBanner> getFileList(List<AppBanner> list ) {
        for(AppBanner _appBanner : list) {
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_appBanner.getUrlId());
            if(_fileIds.size()>0) {
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _appBanner.setFileList(_fileList);
        }
        return list;
    }

}
