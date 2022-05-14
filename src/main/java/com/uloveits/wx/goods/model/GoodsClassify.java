package com.uloveits.wx.goods.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.upload.model.File;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("shop_goods_classify")
public class GoodsClassify implements Serializable {
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
     * 上级分类ID
     */
    private String parentId;

    /**
     * 图片链接
     */
    private String urlId;


    /**
     * 种类
     */
    private Integer type;

    /**
     * 是否上线
     */
    private Integer active;


    /**
     * 排序
     */
    private Integer sort;

    /**
     *  创建时间
     */
    private Date createTime;


    /**
     * 文件上传统一格式
     */
    @TableField(exist = false)
    private List<File> fileList;

    /**
     * 树形结构支持
     */
    @TableField(exist = false)
    private List<GoodsClassify> children;

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

    public String getParentId() {
        return parentId;
    }

    public void setParent(String parentId) {
        this.parentId = parentId;
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

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<GoodsClassify> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsClassify> children) {
        this.children = children;
    }
}
