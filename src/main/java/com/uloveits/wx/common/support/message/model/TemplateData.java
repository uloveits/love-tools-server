package com.uloveits.wx.common.support.message.model;


/**
 * 订阅模板
 * @author  uloveits
 * @date    2020/4/2 10:57
 **/
public class TemplateData {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TemplateData(String value) {
        this.value = value;
    }

}
