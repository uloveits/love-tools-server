package com.uloveits.wx.setting.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.setting.model.AppArticle;
import com.uloveits.wx.setting.model.AppBanner;
import com.uloveits.wx.setting.service.AppArticleService;
import com.uloveits.wx.setting.service.AppBannerService;
import com.uloveits.wx.upload.model.File;
import com.uloveits.wx.upload.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *  文章管理
 *
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
@Api(value = "文章管理", tags = "setting")

@RequestMapping("${api.version.app}/setting")
@RestController
public class ArticleController extends BaseController {

    @Autowired
    private AppArticleService appArticleService;

    @Autowired
    private FileService fileService;


    @ApiOperation(value = "文章添加")
    @PostMapping("/article/add")
    public JsonResult add(HttpServletRequest request, @RequestBody AppArticle param) {
        super.insertActionLog(request,"post:/app/setting/article/add",param.toString());

        AppArticle appArticle = param;
        String fileId = FncUtil.getFileId( param.getFileList());
        appArticle.setUrlId(fileId);

        if(appArticleService.add(appArticle)){
            return JsonResult.ok("操作成功");
        }
        return JsonResult.error("操作失败");
    }

    @ApiOperation(value = "文章修改")
    @PostMapping("/article/update")
    public JsonResult update(HttpServletRequest request,@RequestBody AppArticle param) {
        super.insertActionLog(request,"post:/app/setting/article/update",param.toString());

        String fileId = FncUtil.getFileId( param.getFileList());
        param.setUrlId(fileId);
        if(appArticleService.update(param)){
            return JsonResult.ok("修改成功");
        }else {
            return JsonResult.ok("修改失败");
        }
    }

    @ApiOperation(value = "删除文章", notes = "")
    @PostMapping("/article/delete")
    public JsonResult delete(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/article/delete",param.toString());

        if (appArticleService.deleteById(param.get("id").toString())) {
            return JsonResult.ok("删除成功");
        }else {
            return JsonResult.error("删除失败");
        }

    }

    @ApiOperation(value = "查看所有文章列表")
    @PostMapping("/article/all")
    public PageResult<AppArticle> list(HttpServletRequest request,@RequestBody Map<String, Object> param) throws UnknownHostException {
        super.insertActionLog(request,"post:/app/setting/article/all",param.toString());

        if (param.get("page") == null || param.get("limit") == null) {
            return appArticleService.getList(param);
        }else {
            return appArticleService.getPage(param);
        }
    }

    @ApiOperation(value = "根据Id获得文章详情")
    @PostMapping("/article/detail")
    public JsonResult info (HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/article/detail",param.toString());

        AppArticle info = appArticleService.selectById(param.get("id").toString());
        //补上FileList，用于前端的回显
        List<Integer> _fileIds = FncUtil.getFileIds(info.getUrlId());
        List<File> _fileList = new ArrayList<>();
        if(_fileIds.size()>0){
           _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
        }

        info.setFileList(_fileList);

        return JsonResult.ok("操作成功").put("data",info);
    }

    @ApiOperation(value = "根据remark获得文章详情")
    @PostMapping("/article/detail/remark")
    public JsonResult remark (HttpServletRequest request,@RequestBody Map<String, Object> param) {
        super.insertActionLog(request,"post:/app/setting/article/detail/remark",param.toString());

        AppArticle info = appArticleService.selectOne(new EntityWrapper<AppArticle>().eq("remark",param.get("remark").toString()));
        //补上FileList，用于前端的回显
        List<Integer> _fileIds = FncUtil.getFileIds(info.getUrlId());
        List<File> _fileList = new ArrayList<>();
        if(_fileIds.size()>0){
            _fileList = FncUtil.addIpForPath(fileService.selectBatchIds(_fileIds));
        }

        info.setFileList(_fileList);

        return JsonResult.ok("操作成功").put("data",info);
    }

}
