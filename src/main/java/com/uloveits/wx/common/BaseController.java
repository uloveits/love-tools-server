package com.uloveits.wx.common;

import com.uloveits.wx.common.utils.SystemUtils;
import com.uloveits.wx.system.model.ActionLog;
import com.uloveits.wx.system.model.User;
import com.uloveits.wx.system.service.ActionLogService;
import com.uloveits.wx.system.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录的userId
     */
    public Integer getLoginUserId(HttpServletRequest request) {
        Token token = getLoginToken(request);
        return token == null ? null : Integer.parseInt(token.getUserId());
    }

    public Token getLoginToken(HttpServletRequest request) {
        return SubjectUtil.getToken(request);
    }

    /**
     * 插入操作记录
     */
    public void insertActionLog(HttpServletRequest request,String actionName,String actionContent) {
        Integer userId = this.getLoginUserId(request);
        String userName;
        if(userId != null){
            User user = userService.selectById(userId);
            userName = user.getNickName();
        }else {
            userName = "无拦截接口";
        }

        ActionLog actionLog = new ActionLog();

        actionLog.setUserName(userName);
        actionLog.setActionName(actionName);
        actionLog.setActionContent(actionContent);
        actionLog.setActionIp(SystemUtils.getIpAddr(request));
        actionLogService.insert(actionLog);
    }

}
