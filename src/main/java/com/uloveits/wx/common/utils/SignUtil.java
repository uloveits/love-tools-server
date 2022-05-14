
package com.uloveits.wx.common.utils;


import com.alibaba.fastjson.JSON;
import com.uloveits.wx.common.exception.BusinessException;
import com.uloveits.wx.common.support.context.Resources;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 	微信签名工具类
 * @author lyrics
 * @date 2020-2-12 下午2:37:33
 */
public class SignUtil {

	public static String getAccessToken() {
		String access_token;
		//请求的地址
		String url = "https://api.weixin.qq.com/cgi-bin/token";

		//开发者对应的AppID
		String appId = Resources.THIRDPARTY.getString("app_id_wx");
		//开发者对应的AppSecret
		String appSecret = Resources.THIRDPARTY.getString("app_key_wx");
		String grant_type = "client_credential";
		Map<String, Object> map = new HashMap<>();
		map.put("appid",appId);
		map.put("secret",appSecret);
		map.put("grant_type",grant_type);

		try {
			String wxResValue = HttpUtil.post(url,map);
			System.out.println("========wxResValue:" + wxResValue);
			Map<String, String> tokenMap = (Map) JSON.parse(wxResValue);
			access_token =  tokenMap.get("access_token");
		} catch (Exception e) {
			throw new BusinessException("openId获取失败");
		}
		return access_token;
	}

	public static Map<String, String> JsapiTicket() {

		Map<String, String> result = new HashMap<>();
		//请求的地址
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		String access_token = getAccessToken();
		String type = "jsapi";

		Map<String, Object> map = new HashMap<>();
		map.put("access_token",access_token);
		map.put("type",type);
		try {
			String wxResTicket = HttpUtil.post(url,map);
			System.out.println("========wxResTicket:" + wxResTicket);
			Map<String, String> resMap = (Map) JSON.parse(wxResTicket);
			result.put("errcode", resMap.get("errcode"));
			// 获取微信用户的唯一标识openid
			result.put("errmsg", resMap.get("errmsg"));
			result.put("ticket", resMap.get("ticket"));
			result.put("expires_in", resMap.get("expires_in"));
		} catch (Exception e) {
			throw new BusinessException("JsapiTicket获取失败");
		}
		return result;
	}

	public static Map<String, String> sign(String url) {
		Map<String, String> result = new HashMap<String, String>();

		String noncestr = create_nonce_str();//随机字符串
		String timestamp = create_timestamp();//时间戳
		//4获取url
		//5、将参数排序并拼接字符串
		String str = "jsapi_ticket="+JsapiTicket().get("ticket")+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		System.out.println(str);

		try {
			//6、将字符串进行sha1加密
			String signature = DigestUtils.sha1Hex(str);
			result.put("timestamp",timestamp);
			result.put("noncestr",noncestr);
			result.put("signature",signature);
		} catch (Exception e) {
			throw new BusinessException("获取签名失败");
		}
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
