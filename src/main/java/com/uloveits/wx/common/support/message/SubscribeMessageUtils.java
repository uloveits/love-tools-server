package com.uloveits.wx.common.support.message;


import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.support.message.model.SubscribeMessage;
import com.uloveits.wx.common.support.message.model.TemplateData;
import com.uloveits.wx.common.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 订阅消息
 * @author  uloveits
 * @date    2020/4/2 10:57
**/

public class SubscribeMessageUtils {



    /**
     * 发送订阅消息sendTemplateMessage
     * 小程序订阅消息,发送服务通知
     *
     * @param touser     接收者（用户）的 openid
     * @param templateId 所需下发的模板消息的id
     * @param page       点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    public static void sendTemplateMessage(String touser, String templateId, String page, Map<String, TemplateData> map) {

        String accessToken = SignUtil.getAccessToken();
        System.out.println("##accessToken:  " + accessToken);
        SubscribeMessage subscribeMessage = new SubscribeMessage();
        //拼接数据
        subscribeMessage.setAccess_token(accessToken);
        subscribeMessage.setTouser(touser);
        subscribeMessage.setTemplate_id(templateId);
        subscribeMessage.setPage(page);
        subscribeMessage.setData(map);
        String json = JSONObject.toJSONString(subscribeMessage);
        System.out.println("##订阅发送JSON数据请求:  " + json);

        //借用Unirest 来发送
        JsonNode ret = null;
        try {
            ret = Unirest.post(Resources.THIRDPARTY.getString("sendTemplateMsgUrl")+ accessToken).body(json).asJson().getBody();
        } catch (UnirestException e){
            e.printStackTrace();
        }

        System.out.println(ret);
    }


    public static void main(String[] args) {
        Map<String, TemplateData> map = new HashMap<>();
        map.put("thing1", new TemplateData("L11901283"));
        map.put("time2", new TemplateData("2020-04-02"));
        map.put("thing4", new TemplateData("请及时续费或者退还哦"));
        sendTemplateMessage("oCWJt5bgKCuqV2rEoPgEvCQWc9K8", "PdMrdiLR7DrG2xcwmsHWjLa7bJf3BE4DxgExrvfWXDA", "pages/index/index", map);
    }

}
