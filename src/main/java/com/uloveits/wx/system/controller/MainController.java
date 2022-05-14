package com.uloveits.wx.system.controller;

import com.uloveits.wx.common.BaseController;
import com.uloveits.wx.common.JsonResult;
import com.uloveits.wx.common.utils.StringUtil;
import com.uloveits.wx.common.utils.SystemUtils;
import com.uloveits.wx.system.model.*;
import com.uloveits.wx.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;

import java.util.*;

@Api(value = "个人信息", tags = "main")
@RequestMapping("${api.version}/")
@Controller
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private LoginLogService loginLogService;

    @ResponseBody
    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    @GetMapping("user/info")
    public JsonResult userInfo(HttpServletRequest request) {
        User user = userService.selectById(getLoginUserId(request));
        List<Authorities> auths = new ArrayList<>();
        for (String auth : getLoginToken(request).getPermissions()) {
            Authorities t = new Authorities();
            t.setAuthority(auth);
            auths.add(t);
        }
        user.setAuthorities(auths);
        return JsonResult.ok().put("user", user);
    }

    @ResponseBody
    @ApiOperation(value = "用户登录")
    @PostMapping("user/login")
    public JsonResult login(HttpServletRequest request,@RequestBody Map<String, Object> param) {
        User user = userService.getByUserName(param.get("userName").toString());
        String password = DigestUtils.md5DigestAsHex(param.get("password").toString().getBytes());
        if (user == null) {
            return JsonResult.error("账号不存在");
        } else if (!user.getPassword().equals(password)) {
            return JsonResult.error("密码错误");
        } else if (user.getState() != 1) {
            return JsonResult.error("账号被锁定");
        }
        //添加登陆记录
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(user.getUsername());
        loginLog.setIp(SystemUtils.getIpAddr(request));

        loginLog.setLocation(SystemUtils.getCity(loginLog.getIp()));
        loginLog.setBrowser(SystemUtils.getRequestBrowserInfo(request));
        loginLog.setOs(SystemUtils.getRequestSystemInfo(request));

        loginLogService.insert(loginLog);

        String[] roles = arrayToString(userRoleService.getRoleIds(user.getUserId()));
        String[] permissions = listToArray(authoritiesService.listByUserId(user.getUserId()));
        Token token = tokenStore.createNewToken(String.valueOf(user.getUserId()), permissions, roles);

        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("user",user);
        result.put("accessToken", token.getAccessToken());
        return JsonResult.ok("登录成功").put("data",result);
    }

    @ResponseBody
    @ApiOperation(value = "获取所有菜单")
    @GetMapping("user/read/permission")
    public JsonResult permission(HttpServletRequest request) {
        // 获取当前用户的权限
        Token token = getLoginToken(request);

        String[] roleIds = token.getRoles();
        List<Integer> menuIds = new ArrayList<>();
        for(String roleId:roleIds) {
            //查询该权限下的所有菜单
            List<RoleMenu> roleMenus = roleMenuService.getMenuByRoleId(Integer.parseInt(roleId));
            for(RoleMenu roleMenu: roleMenus) {
                if(!menuIds.contains(roleMenu.getMenuId())) {
                    menuIds.add(roleMenu.getMenuId());
                }
            }
        }

        // 查询所有的菜单
        List<Menu> menus = menuService.selectBatchIds(menuIds);
        for(Menu menu: menus) {
            if(!menuIds.contains(menu.getParentId())) {
                if(!menu.getParentId().equals(-1)) {
                    menuIds.add(menu.getParentId());
                }
            }
        }
        menus = menuService.selectBatchIds(menuIds);
        Collections.sort(menus, Comparator.comparing(Menu::getSortNumber));

        return JsonResult.ok().put("data", getMenuTree(menus, -1));
    }

    //递归转化树形菜单
    private List<Map<String, Object>> getMenuTree(List<Menu> menus, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Menu temp = menus.get(i);
            if (parentId.intValue() == temp.getParentId().intValue()) {
                Map<String, Object> map = new HashMap<>();
                map.put("title", temp.getMenuName());
                map.put("icon", temp.getMenuIcon());
                map.put("key", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
                map.put("children", getMenuTree(menus, menus.get(i).getMenuId()));
                list.add(map);
            }
        }
        return list;
    }

    private String[] listToArray(List<String> list) {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }

    private String[] arrayToString(Object[] objs) {
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = String.valueOf(objs[i]);
        }
        return strs;
    }

}
