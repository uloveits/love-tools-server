package com.uloveits.wx.column.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


@TableName("column_remember")
public class ColumnRemember implements Serializable {
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
     * 是否农历 1 是 0 否
     */
    private String isLunar;

    /**
     * 日期
     */
    private String time;

    /**
     * 农历日期
     */
    private String lunarTimeText;

    /**
     * 农历日期
     */
    private String lunarTimeNum;

    /**
     * 1：在一天数 0：其他
     */
    private String type;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 订阅次数
     */
    private String subscribe;

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

    public String getIsLunar() {
        return isLunar;
    }

    public void setIsLunar(String isLunar) {
        this.isLunar = isLunar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getLunarTimeNum() {
        return lunarTimeNum;
    }

    public void setLunarTimeNum(String lunarTimeNum) {
        this.lunarTimeNum = lunarTimeNum;
    }

    public String getLunarTimeText() {
        return lunarTimeText;
    }

    public void setLunarTimeText(String lunarTimeText) {
        this.lunarTimeText = lunarTimeText;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
}
