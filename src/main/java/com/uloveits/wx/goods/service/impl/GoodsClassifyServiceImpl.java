package com.uloveits.wx.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.goods.dao.GoodsClassifyMapper;
import com.uloveits.wx.goods.model.GoodsClassify;
import com.uloveits.wx.goods.service.GoodsClassifyService;
import com.uloveits.wx.system.model.Menu;
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
public class GoodsClassifyServiceImpl extends ServiceImpl<GoodsClassifyMapper, GoodsClassify> implements GoodsClassifyService {

    @Autowired
    private FileService fileService;

    @Override
    public Boolean add(GoodsClassify goodsClassify) {
        if(super.insert(goodsClassify)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(GoodsClassify goodsClassify){
        if (super.updateById(goodsClassify)) {
            return true;
        }
        return false;
    }


    @Override
    public PageResult<GoodsClassify> getPage(Map<String, Object> param) {
        Map<String,Integer> pageParam = FncUtil.getPageParam(param);

        Page<GoodsClassify> goodsClassify = new Page<>(pageParam.get("page"), pageParam.get("limit"));
        EntityWrapper<GoodsClassify> wrapper = new EntityWrapper<>();

        // 模糊筛选种类
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type_", Integer.parseInt(param.get("type").toString()));
        }
        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }

        wrapper.orderBy("sort", true);

        super.selectPage(goodsClassify,wrapper);

        List<GoodsClassify> goodsClassifyList = goodsClassify.getRecords();

        return new PageResult<>(getFileList(goodsClassifyList), goodsClassify.getTotal());
    }

    @Override
    public PageResult<GoodsClassify> getList(Map<String, Object> param) {

        EntityWrapper<GoodsClassify> wrapper = new EntityWrapper<>();

        // 模糊筛选种类
        if (param.get("type") != null && StringUtil.isNotBlank(param.get("type").toString())) {
            wrapper.eq("type_", Integer.parseInt(param.get("type").toString()));
        }
        // 模糊筛选状态
        if (param.get("active") != null && StringUtil.isNotBlank(param.get("active").toString())) {
            wrapper.eq("active", Integer.parseInt(param.get("active").toString()));
        }

        wrapper.orderBy("sort", true);

        List<GoodsClassify> list = super.selectList(wrapper);

        return new PageResult<>(getFileList(list));
    }

    @Override
    public List<GoodsClassify> getTreeList(Map<String, Object> param) {

        List<GoodsClassify> list = getFileList(super.selectList( new EntityWrapper<GoodsClassify>().orderBy("sort", true)));


        return getGoodsClassTree(list,"-1");
    }

    @Override
    public List<Map<String, Object>> getSelectTree(Map<String, Object> param) {

        List<GoodsClassify> list = getFileList(super.selectList( new EntityWrapper<GoodsClassify>().orderBy("sort", true)));


        return getGoodsSelectTree(list,"-1");
    }

    /**
     * 转换成树形结构
     * @param classify
     * @param parentId
     * @return
     */
    private List<GoodsClassify> getGoodsClassTree(List<GoodsClassify> classify, String parentId) {
        List<GoodsClassify> list = new ArrayList<>();
        for (int i = 0; i < classify.size(); i++) {
            GoodsClassify temp = classify.get(i);
            if (parentId.equals(temp.getParentId())) {
                temp.setChildren(getGoodsClassTree(classify, classify.get(i).getId()));
                list.add(temp);
            }
        }
        return list;
    }

    /**
     * 转换成下来框树形结构
     * @param classify
     * @param parentId
     * @return
     */
    private List<Map<String, Object>> getGoodsSelectTree(List<GoodsClassify> classify, String parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < classify.size(); i++) {
            GoodsClassify temp = classify.get(i);
            if (parentId.equals(temp.getParentId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("title", temp.getName());
                map.put("value", temp.getId());
                map.put("key", temp.getId());
                map.put("children", getGoodsSelectTree(classify, classify.get(i).getId()));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 通过urlId获取文件列表
     * @param list
     * @return
     */
    private List<GoodsClassify> getFileList(List<GoodsClassify> list ) {
        for(GoodsClassify _goodsClassify : list) {
            List<File> _fileList = new ArrayList<>();
            List<Integer> _fileIds = FncUtil.getFileIds(_goodsClassify.getUrlId());
            if(_fileIds.size()>0){
                _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
            }
            _goodsClassify.setFileList(_fileList);
        }
        return list;
    }

}
