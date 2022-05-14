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


@TableName("shop_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String no;

    /**
     * 商品总金额(不含优惠折扣)
     */
    private String totalPrice;

    /**
     * 订单金额(含优惠折扣)
     */
    private String orderPrice;

    /**
     * 优惠券id
     */
    private String couponId;

    /**
     * 优惠券抵扣金额
     */
    private String couponMoney;

    /**
     * 积分抵扣金额
     */
    private String pointsMoney;

    /**
     * 积分抵扣数量
     */
    private Integer pointsNum;

    /**
     * 实际付款金额(包含运费)
     */
    private String payPrice;

    /**
     * 买家留言
     */
    @TableField("buyer_remark")
    private String remark;

    /**
     * 支付方式(1余额支付 2微信支付)
     */
    private Integer payType;

    /**
     * 运费金额
     */
    @TableField("express_price")
    private String postFee;

    /**
     * 物流公司id
     */
    private String expressId;

    /**
     * 物流公司单号
     */
    private String expressNo;


    /**
     * 微信支付交易号
     */
    private String transactionId;


    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer status;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货地址id
     */
    private String addressId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 聚合OrderGoods
     */
    @TableField(exist = false)
    private List<OrderGoods> orderGoods;

    /**
     * 聚合地址
     */
    @TableField(exist = false)
    private AppUserAddress address;

    /**
     * 聚合用户信息
     */
    @TableField(exist = false)
    private AppUser user;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getPointsMoney() {
        return pointsMoney;
    }

    public void setPointsMoney(String pointsMoney) {
        this.pointsMoney = pointsMoney;
    }

    public Integer getPointsNum() {
        return pointsNum;
    }

    public void setPointsNum(Integer pointsNum) {
        this.pointsNum = pointsNum;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public AppUserAddress getAddress() {
        return address;
    }

    public void setAddress(AppUserAddress address) {
        this.address = address;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
