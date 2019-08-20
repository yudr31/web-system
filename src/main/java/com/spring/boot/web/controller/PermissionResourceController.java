package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.web.service.PermissionResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2019-7-18 16:48:28
 */
@Controller
@RequestMapping("/permissionResource")
public class PermissionResourceController extends BasePageController {

    @Autowired
    private PermissionResourceService permissionResourceService;

    @RequestMapping("/permissionResourceList")
    public String permissionResourceList(PermissionResource permissionResource, ModelMap modelMap){
        return getPermissionResourceList(permissionResource,modelMap);
    }

    private String getPermissionResourceList(PermissionResource permissionResource, ModelMap modelMap){
        PageInfo<PermissionResource> pageInfo = permissionResourceService.permissionResourcePage(permissionResource);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",permissionResource);
        return "permissionResource/permissionResourceList";
    }

    @RequestMapping("/permissionResourceListForCheck")
    public String permissionResourceListForCheck(PermissionResource permissionResource, String lookUpType, ModelMap modelMap){
        getPermissionResourceList(permissionResource,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "permissionResource/permissionResourceListForCheck";
    }

    @RequestMapping("/permissionResourceCreator")
    public String permissionResourceCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getPermissionResourceViewer(null,modelMap);
        return "permissionResource/permissionResourceViewForCreator";
    }

    @RequestMapping("/permissionResourceCreator.do")
    public ModelAndView permissionResourceCreator(PermissionResource permissionResource){
        Integer result = permissionResourceService.addPermissionResource(permissionResource);
        if (result > 0)
            return getSuccessResponseWithNewId(permissionResource.getGid());
        return getErrorResponse("添加系统管理用户信息失败");
    }

    @RequestMapping("/permissionResourceEditor")
    public String permissionResourceEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getPermissionResourceViewer(gid,modelMap);
        return "permissionResource/permissionResourceViewForEditor";
    }

    @RequestMapping("/permissionResourceEditor.do")
    public ModelAndView permissionResourceEditor(PermissionResource permissionResource){
        Integer result = permissionResourceService.updatePermissionResource(permissionResource);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改系统管理用户信息失败");
    }

    @RequestMapping("/permissionResourceViewer")
    public String permissionResourceViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getPermissionResourceViewer(gid,modelMap);
    }

    public String getPermissionResourceViewer(Long gid, ModelMap modelMap){
        PermissionResource permissionResource = null != gid ? permissionResourceService.permissionResourceDetail(gid) : new PermissionResource();
        modelMap.addAttribute("data",permissionResource);
        return "permissionResource/permissionResourceViewForViewer";
    }

}
