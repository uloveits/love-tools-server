package com.uloveits.wx.column.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.uloveits.wx.user.model.AppUser;

import java.io.Serializable;
import java.util.Date;


@TableName("column_cash_gift")
public class ColumnCashGift implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 金额（元）
     */
    private String money;

    /**
     * 描述
     */
    private String des;

    /**
     * 1:收礼 2:随礼
     */
    private String type;

    /**
     * 日期
     */
    private String date;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private AppUser createInfo;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public AppUser getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(AppUser createInfo) {
        this.createInfo = createInfo;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
