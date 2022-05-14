package com.uloveits.wx.common.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import com.uloveits.wx.common.support.context.Resources;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lyrics
 */
public class SystemUtils {
    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip!= null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip!= null && !"".equals(ip)  && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    public static String getCity(String ip) {
        // 创建 GeoLite2 数据库
        File database = new File(Resources.APPLICATION.getString("dbPath"));
        // 读取数据库内容
        try {
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);

            // 获取查询结果
            CityResponse response = reader.city(ipAddress);

            // 获取国家信息
            Country country = response.getCountry();
            // 获取省份
            Subdivision subdivision = response.getMostSpecificSubdivision();
            // 获取城市
            City city = response.getCity();

            return country.getNames().get("zh-CN") + " "
                    + subdivision.getNames().get("zh-CN")+ " "
                    + city.getNames().get("zh-CN");
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 获取来访者的浏览器版本
     * @param request
     * @return
     */
    public static String getRequestBrowserInfo(HttpServletRequest request){
        String browserVersion = null;
        String header = request.getHeader("user-agent");
        if(header == null || header.equals("")){
            return "";
        }
        if(header.indexOf("MSIE")>0){
            browserVersion = "IE";
        }else if(header.indexOf("Firefox")>0){
            browserVersion = "Firefox";
        }else if(header.indexOf("Chrome")>0){
            browserVersion = "Chrome";
        }else if(header.indexOf("Safari")>0){
            browserVersion = "Safari";
        }else if(header.indexOf("Camino")>0){
            browserVersion = "Camino";
        }else if(header.indexOf("Konqueror")>0){
            browserVersion = "Konqueror";
        }
        return browserVersion;
    }

    /**
     * 获取系统版本信息
     * @param request
     * @return
     */
    public static String getRequestSystemInfo(HttpServletRequest request){
        String systemInfo = null;
        String header = request.getHeader("user-agent");
        if(header == null || header.equals("")){
            return "";
        }
        //得到用户的操作系统
        if (header.indexOf("NT 6.0") > 0){
            systemInfo = "Windows Vista/Server 2008";
        } else if (header.indexOf("NT 5.2") > 0){
            systemInfo = "Windows Server 2003";
        } else if (header.indexOf("NT 5.1") > 0){
            systemInfo = "Windows XP";
        } else if (header.indexOf("NT 6.0") > 0){
            systemInfo = "Windows Vista";
        } else if (header.indexOf("NT 6.1") > 0){
            systemInfo = "Windows 7";
        } else if (header.indexOf("NT 6.2") > 0){
            systemInfo = "Windows Slate";
        } else if (header.indexOf("NT 10.0") > 0){
            systemInfo = "Windows 10";
        } else if (header.indexOf("Mac") > 0){
            systemInfo = "Mac";
        } else if (header.indexOf("Unix") > 0){
            systemInfo = "UNIX";
        } else if (header.indexOf("Linux") > 0){
            systemInfo = "Linux";
        } else if (header.indexOf("SunOS") > 0){
            systemInfo = "SunOS";
        }
        return systemInfo;
    }

    /**
     * 获取来访者的主机名称
     * @param ip
     * @return
     */
    public static String getHostName(String ip) {
        InetAddress inet;
        try {
            inet = InetAddress.getByName(ip);
            return inet.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }


}
