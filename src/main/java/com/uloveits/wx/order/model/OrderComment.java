package com.uloveits.wx.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.user.model.AppUser;
import com.uloveits.wx.user.model.AppUserAddress;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("shop_order_comment")
public class OrderComment implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 商品Id
     */
    private String goodsId;


    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品Sku
     */
    private String skuValueNames;

    /**
     * 评分
     */
    private Integer grade;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSkuValueNames() {
        return skuValueNames;
    }

    public void setSkuValueNames(String skuValueNames) {
        this.skuValueNames = skuValueNames;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
