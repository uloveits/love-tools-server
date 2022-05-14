package com.uloveits.wx.goods.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.upload.model.File;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("shop_goods")
public class Goods implements Serializable {
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
     * 商品分类Id
     */
    private String goodsClassifyId;

    /**
     * sku分类Id
     */
    private String skuClassifyId;

    /**
     * 商品介绍
     */
    @TableField("goods_intro")
    private String intro;

    /**
     * 图片链接
     */
    private String urlId;

    /**
     * 规格类型
     */
    private String skuType;

    /**
     * 商品详情
     */
    private String content;

    /**
     * 初始销量
     */
    private String salesInit;

    /**
     * 实际销量
     */
    private String salesActual;

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
     * 聚合GoodsSku
     */
    @TableField(exist = false)
    private List<GoodsSku> skuValue;

    /**
     * 聚合分类
     */
    @TableField(exist = false)
    private GoodsClassify classify;

    /**
     * 聚合sku
     */
    @TableField(exist = false)
    private List<Sku> sku;

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

    public String getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(String goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getSkuClassifyId() {
        return skuClassifyId;
    }

    public void setSkuClassifyId(String skuClassifyId) {
        this.skuClassifyId = skuClassifyId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSalesInit() {
        return salesInit;
    }

    public void setSalesInit(String salesInit) {
        this.salesInit = salesInit;
    }

    public String getSalesActual() {
        return salesActual;
    }

    public void setSalesActual(String salesActual) {
        this.salesActual = salesActual;
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

    public List<GoodsSku> getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(List<GoodsSku> skuValue) {
        this.skuValue = skuValue;
    }

    public GoodsClassify getClassify() {
        return classify;
    }

    public void setClassify(GoodsClassify classify) {
        this.classify = classify;
    }

    public List<Sku> getSku() {
        return sku;
    }

    public void setSku(List<Sku> sku) {
        this.sku = sku;
    }
}
