package com.uloveits.wx.upload.service;

import com.baomidou.mybatisplus.service.IService;

import com.uloveits.wx.upload.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService extends IService<File> {
    /**
     * 上传文件
     */
    List<File> upload(MultipartFile file) throws IOException;

    List<File> selectByPath(String path);

    Boolean delete(Integer id);

//    /**
//     * 分块上传文件
//     */
//    Void uploadWithBlock(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file);
//
//    /**
//     * 检查Md5判断文件是否已上传
//     * @param md5
//     * @return
//     */
//    Boolean checkMd5(String md5);

}
