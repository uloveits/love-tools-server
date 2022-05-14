package com.uloveits.wx.upload.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.utils.FileUtils;
import com.uloveits.wx.common.utils.FncUtil;
import com.uloveits.wx.upload.dao.FileMapper;
import com.uloveits.wx.upload.model.File;

import com.uloveits.wx.upload.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * 用户Service
 * @author lyrics
 * @date 2019/11/05
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    /**
     * 上传文件
     * @param md5
     * @param file
     */
    @Override
    public List<File> upload(MultipartFile file) throws IOException {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String generateFileName =  FileUtils.generateFileName();
        String path = uploadFolder + '/'+ generateFileName + '.'+suffix;
        String url = '/'+generateFileName + '.'+suffix;
        FileUtils.write(path, file.getInputStream());

        //赋值都实体类
        File upload = new File();
        upload.setName(generateFileName);
        upload.setSize(file.getSize());
        upload.setUrl(url);
        if(super.insert(upload)) {
            return FncUtil.addIpForPath(super.selectList(new EntityWrapper().eq("name", generateFileName)));
        }else {
            throw new BusinessException("文件上传失败");
        }
    }


    /**
     * 通过路径查文件
     * @param path
     * @return
     */
    @Override
    public List<File> selectByPath(String path) {
       return FncUtil.addIpForPath(super.selectList(new EntityWrapper<File>().eq("path", path)));
    }

    @Override
    public Boolean delete(Integer id) {
       //先找到该文件的路径信息
        File file = super.selectById(id);
        //先删除源文件
        String path = Resources.APPLICATION.getString("file.uploadFolder") + file.getUrl().substring(1);
        if(FileUtils.delete(path)){
            //删除数据库
            if(super.deleteById(Long.parseLong(id.toString()))){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }



//    /**
//     * 分块上传文件
//     * @param md5
//     * @param size
//     * @param chunks
//     * @param chunk
//     * @param file
//     * @throws IOException
//     */
//    @Override
//    public Void uploadWithBlock(String name,
//                                String md5,
//                                Long size,
//                                Integer chunks,
//                                Integer chunk,
//                                MultipartFile file) {
////        String fileName = getFileName(md5, chunks);
////        FileUtils.writeWithBlok(UploadConfig.path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
////        addChunk(md5,chunk);
////        if (isUploaded(md5)) {
////            removeKey(md5);
////            fileDao.save(new File(name, md5,UploadConfig.path + fileName, new Date()));
////        }
//    }
//
//    /**
//     * 检查Md5判断文件是否已上传
//     * @param md5
//     * @return
//     */
//    @Override
//    public Boolean checkMd5(String md5) {
////        File file = new File();
////        file.setMd5(md5);
////        return baseMapper.getByFile(file) == null;
//        return false;
//    }
}
