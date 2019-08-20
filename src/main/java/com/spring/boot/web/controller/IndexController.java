package com.spring.boot.web.controller;

import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.common.exception.BaseException;
import com.spring.boot.common.util.SpringContextUtil;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.web.manager.PermissionManager;
import com.spring.boot.web.manager.UserManager;
import com.spring.boot.web.service.AdminInfoService;
import com.spring.boot.web.service.PermissionInfoService;
import com.spring.boot.web.service.PermissionMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: yuderen
 * @version: 1.0 2017-9-16 20:49
 */
@Controller
public class IndexController extends BasePageController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private AdminInfoService adminInfoService;
    @Autowired
    private PermissionMenuService permissionMenuService;
    @Autowired
    private PermissionInfoService permissionInfoService;

    @RequestMapping("/")
    public String index( ModelMap modelMap){
        if (!userManager.isLoggedIn()) {
            return "index/login";
        }
        LoginUserVO loginUserVO = userManager.getLoginUserVO();
        List<PermissionMenuVO> itemMenuList = permissionManager.getPermissionResourceByRedis();
        if (null == itemMenuList){
            itemMenuList = permissionInfoService.fetchPermissionMenuList(loginUserVO.getUserId());
            permissionManager.setPermissionResourceByRedis(itemMenuList);
        }
        modelMap.addAttribute("itemMenuList", itemMenuList);
        return "index/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        return "index/login";
    }

    @RequestMapping(value = "/loginDialog", method = RequestMethod.GET)
    public String loginDialog(ModelMap modelMap) {
        return "index/loginDialog";
    }

    @RequestMapping("/newLogin.do")
    public ModelAndView newLogin(AdminInfo adminInfo){
        String userName = SpringContextUtil.getRequest().getParameter("username");
        adminInfo.setUserName(userName);
        try {
            LoginUserVO loginUserVO = adminInfoService.login(adminInfo);
            userManager.responseAuthLoginToken(loginUserVO.getToken());
            userManager.setUnifiedUserToRedis(loginUserVO, loginUserVO.getToken());
            return getSuccessResponse();
        } catch (Exception e){
            if (e instanceof BaseException){
                BaseException ex = (BaseException) e;
                return getErrorResponse(ex.getMessage());
            }
        }
        return getErrorResponse("登录失败");
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        userManager.logout();
        return "index/login";
    }

}
