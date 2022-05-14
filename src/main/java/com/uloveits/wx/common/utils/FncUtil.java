package com.uloveits.wx.common.utils;

import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.upload.model.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyrics
 */
public class FncUtil {

    /**
     * 获得IP
     * @param target
     * @param src
     * @throws IOException
     */
    public static String getIp() {
        return Resources.APPLICATION.getString("file.prefix");
    }



    /**
     * 获得文件的FileId，用逗号隔开
     * @return
     */
    public static String getFileId( List<File> fileList) {
        String fileId = "";
        if(fileList != null){
            for(int i= 0; i< fileList.size(); i++) {
                if (i < fileList.size()-1) {
                    fileId += fileList.get(i).getId().toString() + ',';
                }else {
                    fileId += fileList.get(i).getId().toString();
                }
            }
        }

        return fileId;
    }
    /**
     * 通过fileIds字符串获得数组
     * @param fileList
     * @return
     */
    public static List<Integer> getFileIds(String urlId) {
        List<Integer> _fileIds = new ArrayList<>();
        if(StringUtil.isNotBlank(urlId)){
            String[] fileIds = urlId.split(",");
            for(String fileId :fileIds) {
                _fileIds.add(Integer.parseInt(fileId));
            }
        }
        return _fileIds;
    }

    /**
     * 格式化文件路径
     * @param fileList
     * @return
     */
    public static List<File> addIpForPath(List<File> fileList) {
        for(File file : fileList) {
            file.setUrl(getIp() +file.getUrl());
        }
        return fileList;
    }

    /**
     * 获得分页参数
     * @return
     */
    public static Map<String,Integer> getPageParam(Map<String,Object> param) {
        Integer page = 0;
        Integer limit = 10;

        if (StringUtil.isNotBlank(param.get("page").toString())) {
            page = Integer.valueOf(param.get("page").toString());
        }
        if (StringUtil.isNotBlank(param.get("limit").toString())) {
            limit = Integer.valueOf(param.get("limit").toString());
        }
        Map<String,Integer> pageParam = new HashMap<>();
        pageParam.put("page",page);
        pageParam.put("limit",limit);

        return pageParam;
    }


}
