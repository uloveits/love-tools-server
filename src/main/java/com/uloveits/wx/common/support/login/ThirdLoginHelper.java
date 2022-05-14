package com.uloveits.wx.common.support.login;

import com.alibaba.fastjson.JSON;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.support.context.Resources;
import com.uloveits.wx.common.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 第三方登录辅助类
 * @author uloveits
 * @version 1.0, 2019/11/14
 */
public class ThirdLoginHelper {

    /**
     * 获取微信的认证token和用户OpenID
     *
     * @param code
     * @return
     */
    public static final Map<String, String> getWxTokenAndOpenid(String code) {
        Map<String, String> resMap = new HashMap<String, String>();
        String js_code = code;
        //请求的地址
        String url = Resources.THIRDPARTY.getString("code2Session");
        //开发者对应的AppID
        String appId = Resources.THIRDPARTY.getString("app_id_wx");
        //开发者对应的AppSecret
        String appSecret = Resources.THIRDPARTY.getString("app_key_wx");
        String grant_type =Resources.THIRDPARTY.getString("grant_type");
        Map<String, Object> map = new HashMap<>();
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",js_code);
        map.put("grant_type",grant_type);

        String wxResValue = HttpUtil.post(url,map);
        System.out.println("========wxResValue:" + wxResValue);
        if (wxResValue != null && wxResValue.indexOf("session_key") > -1) {
            Map<String, String> tokenMap = (Map) JSON.parse(wxResValue);
            resMap.put("access_token", tokenMap.get("session_key"));
            // 获取微信用户的唯一标识openid
            resMap.put("openId", tokenMap.get("openid"));
        } else {
            throw new BusinessException("openId获取失败");
        }
        return resMap;
    }
}
