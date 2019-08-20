package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.permission.PermissionUser;
import com.spring.boot.web.service.PermissionUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2019-7-18 16:46:33
 */
@Controller
@RequestMapping("/permissionUser")
public class PermissionUserController extends BasePageController {

    @Autowired
    private PermissionUserService permissionUserService;

    @RequestMapping("/permissionUserList")
    public String permissionUserList(PermissionUser permissionUser, ModelMap modelMap){
        return getPermissionUserList(permissionUser,modelMap);
    }

    private String getPermissionUserList(PermissionUser permissionUser, ModelMap modelMap){
        PageInfo<PermissionUser> pageInfo = permissionUserService.permissionUserPage(permissionUser);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",permissionUser);
        return "permissionUser/permissionUserList";
    }

    @RequestMapping("/permissionUserListForCheck")
    public String permissionUserListForCheck(PermissionUser permissionUser, String lookUpType, ModelMap modelMap){
        getPermissionUserList(permissionUser,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "permissionUser/permissionUserListForCheck";
    }

    @RequestMapping("/permissionUserCreator")
    public String permissionUserCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getPermissionUserViewer(null,modelMap);
        return "permissionUser/permissionUserViewForCreator";
    }

    @RequestMapping("/permissionUserCreator.do")
    public ModelAndView permissionUserCreator(PermissionUser permissionUser){
        Integer result = permissionUserService.addPermissionUser(permissionUser);
        if (result > 0)
            return getSuccessResponseWithNewId(permissionUser.getGid());
        return getErrorResponse("添加系统管理用户信息失败");
    }

    @RequestMapping("/permissionUserEditor")
    public String permissionUserEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getPermissionUserViewer(gid,modelMap);
        return "permissionUser/permissionUserViewForEditor";
    }

    @RequestMapping("/permissionUserEditor.do")
    public ModelAndView permissionUserEditor(PermissionUser permissionUser){
        Integer result = permissionUserService.updatePermissionUser(permissionUser);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改系统管理用户信息失败");
    }

    @RequestMapping("/permissionUserViewer")
    public String permissionUserViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getPermissionUserViewer(gid,modelMap);
    }

    public String getPermissionUserViewer(Long gid, ModelMap modelMap){
        PermissionUser permissionUser = null != gid ? permissionUserService.permissionUserDetail(gid) : new PermissionUser();
        modelMap.addAttribute("data",permissionUser);
        return "permissionUser/permissionUserViewForViewer";
    }

}
