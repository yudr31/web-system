package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.web.service.PermissionRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2019-7-18 16:45:02
 */
@Controller
@RequestMapping("/permissionRole")
public class PermissionRoleController extends BasePageController {

    @Autowired
    private PermissionRoleService permissionRoleService;

    @RequestMapping("/permissionRoleList")
    public String permissionRoleList(PermissionRole permissionRole, ModelMap modelMap){
        return getPermissionRoleList(permissionRole,modelMap);
    }

    private String getPermissionRoleList(PermissionRole permissionRole, ModelMap modelMap){
        PageInfo<PermissionRole> pageInfo = permissionRoleService.permissionRolePage(permissionRole);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",permissionRole);
        return "permissionRole/permissionRoleList";
    }

    @RequestMapping("/permissionRoleListForCheck")
    public String permissionRoleListForCheck(PermissionRole permissionRole, String lookUpType, ModelMap modelMap){
        getPermissionRoleList(permissionRole,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "permissionRole/permissionRoleListForCheck";
    }

    @RequestMapping("/permissionRoleCreator")
    public String permissionRoleCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getPermissionRoleViewer(null,modelMap);
        return "permissionRole/permissionRoleViewForCreator";
    }

    @RequestMapping("/permissionRoleCreator.do")
    public ModelAndView permissionRoleCreator(PermissionRole permissionRole){
        Integer result = permissionRoleService.addPermissionRole(permissionRole);
        if (result > 0)
            return getSuccessResponseWithNewId(permissionRole.getGid());
        return getErrorResponse("添加系统管理用户信息失败");
    }

    @RequestMapping("/permissionRoleEditor")
    public String permissionRoleEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getPermissionRoleViewer(gid,modelMap);
        return "permissionRole/permissionRoleViewForEditor";
    }

    @RequestMapping("/permissionRoleEditor.do")
    public ModelAndView permissionRoleEditor(PermissionRole permissionRole){
        Integer result = permissionRoleService.updatePermissionRole(permissionRole);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改系统管理用户信息失败");
    }

    @RequestMapping("/permissionRoleViewer")
    public String permissionRoleViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getPermissionRoleViewer(gid,modelMap);
    }

    public String getPermissionRoleViewer(Long gid, ModelMap modelMap){
        PermissionRole permissionRole = null != gid ? permissionRoleService.permissionRoleDetail(gid) : new PermissionRole();
        modelMap.addAttribute("data",permissionRole);
        return "permissionRole/permissionRoleViewForViewer";
    }

}
