package com.uloveits.wx.upload.controller;


import com.uloveits.wx.common.BaseController;

import com.uloveits.wx.common.JsonResult;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "文件上传", tags = "upload")
@RestController
@RequestMapping("${api.version.app}/")
public class FileUploadController extends BaseController {
    @Autowired
    private FileService fileService;


    @ApiOperation(value = "上传文件", notes = "")
    @PostMapping("/upload")
    public JsonResult upload(HttpServletRequest request, MultipartFile file) throws IOException {
        super.insertActionLog(request,"post:/app/upload","{}");

         List<File> list = fileService.upload(file);

         if(list.size() > 0) {
             return JsonResult.ok("上传成功").put("data",list);
         }else {
             return JsonResult.error("上传失败");
         }
    }

    @ApiOperation(value = "删除文件", notes = "")
    @PostMapping("/upload/delete")
    public JsonResult delete(HttpServletRequest request, @RequestBody Map<String,Object> param) {
        super.insertActionLog(request,"post:/app/upload/delete",param.toString());

        if(fileService.delete(Integer.parseInt(param.get("id").toString()))){
            return JsonResult.ok("删除成功!");
        }else {
            return JsonResult.error("删除失败");
        }
    }


}
