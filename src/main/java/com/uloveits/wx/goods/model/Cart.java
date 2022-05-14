package com.uloveits.wx.goods.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;



@TableName("shop_cart_item")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 商品规格组id
     */
    private String skuValueIds;

    /**
     * 商品规格组名称
     */
    private String skuValueNames;

    /**
     * 商品数量
     */
    @TableField("goods_count")
    private Integer count;

    /**
     * 商品数量
     */
    @TableField("goods_price")
    private String price;


    /**
     * 是否选中
     */
    private Integer checked;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  创建时间
     */
    private Date updateTime;

    /**
     * 聚合商品
     */
    @TableField(exist = false)
    private Goods goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getSkuValueNames() {
        return skuValueNames;
    }

    public void setSkuValueNames(String skuValueNames) {
        this.skuValueNames = skuValueNames;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
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


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
