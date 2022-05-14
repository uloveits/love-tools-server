package com.uloveits.wx.setting.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.setting.dao.AppNavigateMapper;
import com.uloveits.wx.setting.model.AppNavigate;
import com.uloveits.wx.setting.service.AppNavigateService;
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
public class AppNavigateServiceImpl extends ServiceImpl<AppNavigateMapper, AppNavigate> implements AppNavigateService {

    @Autowired
    private FileService fileService;

    @Override
    public Boolean add(AppNavigate appNavigate) {
        if(super.insert(appNavigate)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(AppNavigate appNavigate){
        if (super.updateById(appNavigate)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<AppNavigate> getPage(Map<String, Object> param) {
        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }

        Page<AppNavigate> appNavigate = new Page<>(page, limit);
        EntityWrapper<AppNavigate> wrapper = new EntityWrapper<>();


        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }


        wrapper.orderBy("sort", true);

        super.selectPage(appNavigate,wrapper);

        List<AppNavigate> appNavigateList = appNavigate.getRecords();

        return new PageResult<>(getFileList(appNavigateList), appNavigate.getTotal());
    }

    @Override
    public PageResult<AppNavigate> getList(Map<String, Object> param) {

        EntityWrapper<AppNavigate> wrapper = new EntityWrapper<>();

        // 模糊筛选种类
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type_", Integer.parseInt(param.get("type").toString()));
        }
        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }



        wrapper.orderBy("sort", true);

        List<AppNavigate> list = super.selectList(wrapper);


        return new PageResult<>(getFileList(list));
    }

    /**
     * 通过urlId获取文件列表
     * @param list
     * @return
     */
    public List<AppNavigate> getFileList(List<AppNavigate> list ) {
        for(AppNavigate _appNavigate : list) {
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_appNavigate.getUrlId());
            if(_fileIds.size()>0) {
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _appNavigate.setFileList(_fileList);
        }
        return list;
    }

}
