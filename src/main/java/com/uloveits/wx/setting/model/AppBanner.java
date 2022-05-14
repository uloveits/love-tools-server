package com.uloveits.wx.setting.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.upload.model.File;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("app_banner")
public class AppBanner implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片链接
     */
    private String urlId;

    /**
     * 绑定链接
     */
    private String linkId;

    /**
     * 种类
     */
    @TableField("type_")
    private Integer type;

    /**
     * 是否上线
     */
    private Integer active;

    /**
     * 上传人
     */
    private Integer createId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     *  创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private List<File> fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCreateId() {
        return createId;
    }


    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
