package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.web.service.PermissionMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2019-7-18 16:27:05
 */
@Controller
@RequestMapping("/permissionMenu")
public class PermissionMenuController extends BasePageController {

    @Autowired
    private PermissionMenuService permissionMenuService;

    @RequestMapping("/permissionMenuList")
    public String permissionMenuList(PermissionMenu permissionMenu, ModelMap modelMap){
        return getPermissionMenuList(permissionMenu,modelMap);
    }

    private String getPermissionMenuList(PermissionMenu permissionMenu, ModelMap modelMap){
        PageInfo<PermissionMenu> pageInfo = permissionMenuService.permissionMenuPage(permissionMenu);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",permissionMenu);
        return "permissionMenu/permissionMenuList";
    }

    @RequestMapping("/permissionMenuListForCheck")
    public String permissionMenuListForCheck(PermissionMenu permissionMenu, String lookUpType, ModelMap modelMap){
        getPermissionMenuList(permissionMenu,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "permissionMenu/permissionMenuListForCheck";
    }

    @RequestMapping("/permissionMenuCreator")
    public String permissionMenuCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getPermissionMenuViewer(null,modelMap);
        return "permissionMenu/permissionMenuViewForCreator";
    }

    @RequestMapping("/permissionMenuCreator.do")
    public ModelAndView permissionMenuCreator(PermissionMenu permissionMenu){
        Integer result = permissionMenuService.addPermissionMenu(permissionMenu);
        if (result > 0)
            return getSuccessResponseWithNewId(permissionMenu.getGid());
        return getErrorResponse("添加系统管理用户信息失败");
    }

    @RequestMapping("/permissionMenuEditor")
    public String permissionMenuEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getPermissionMenuViewer(gid,modelMap);
        return "permissionMenu/permissionMenuViewForEditor";
    }

    @RequestMapping("/permissionMenuEditor.do")
    public ModelAndView permissionMenuEditor(PermissionMenu permissionMenu){
        Integer result = permissionMenuService.updatePermissionMenu(permissionMenu);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改系统管理用户信息失败");
    }

    @RequestMapping("/permissionMenuViewer")
    public String permissionMenuViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getPermissionMenuViewer(gid,modelMap);
    }

    public String getPermissionMenuViewer(Long gid, ModelMap modelMap){
        PermissionMenu permissionMenu = null != gid ? permissionMenuService.permissionMenuDetail(gid) : new PermissionMenu();
        modelMap.addAttribute("data",permissionMenu);
        return "permissionMenu/permissionMenuViewForViewer";
    }

}
