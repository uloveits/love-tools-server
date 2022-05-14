package com.uloveits.wx.column.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.column.dao.ColumnClassifyMapper;
import com.uloveits.wx.column.model.ColumnClassify;
import com.uloveits.wx.column.service.ColumnClassifyService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.dao.GoodsClassifyMapper;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.service.GoodsClassifyService;
import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class ColumnClassifyServiceImpl extends ServiceImpl<ColumnClassifyMapper, ColumnClassify> implements ColumnClassifyService {




    @Override
    public Boolean update(ColumnClassify columnClassify){
        if (super.insertOrUpdate(columnClassify)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<ColumnClassify> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<ColumnClassify> columnClassify = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<ColumnClassify> wrapper = new EntityWrapper<>();


        wrapper.orderBy("sort", true);

        super.selectPage(columnClassify,wrapper);

        List<ColumnClassify> columnClassifyList = columnClassify.getRecords();

        return new PageResult<>(columnClassifyList, columnClassify.getTotal());
    }

    @Override
    public PageResult<ColumnClassify> getList(Map<String, Object> param) {

        EntityWrapper<ColumnClassify> wrapper = new EntityWrapper<>();

        wrapper.orderBy("sort", true);

        List<ColumnClassify> list = super.selectList(wrapper);

        return new PageResult<>(list);
    }

}
