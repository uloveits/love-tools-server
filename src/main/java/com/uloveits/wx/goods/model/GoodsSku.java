package com.uloveits.wx.goods.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.upload.model.File;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("shop_goods_sku")
public class GoodsSku implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 商品规格分类id
     */
    private String skuClassifyId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品规格值组合Id
     */
    private String skuValueIds;

    /**
     * 规格图片Id
     */
    private String urlId;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品价格
     */
    @TableField("goods_price")
    private String price;

    /**
     * 商品划线价
     */
    private String linePrice;

    /**
     * 商品库存
     */
    private String stock;

    /**
     * 商品销量
     */
    @TableField("goods_sales")
    private String sales;


    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

    /**
     * 文件上传统一格式
     */
    @TableField(exist = false)
    private List<File> fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuClassifyId() {
        return skuClassifyId;
    }

    public void setSkuClassifyId(String skuClassifyId) {
        this.skuClassifyId = skuClassifyId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSkuValueIds() {
        return skuValueIds;
    }

    public void setSkuValueIds(String skuValueIds) {
        this.skuValueIds = skuValueIds;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(String linePrice) {
        this.linePrice = linePrice;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
