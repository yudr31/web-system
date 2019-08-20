package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.web.service.AdminInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2018-9-13 16:48:35
 */
@Controller
@RequestMapping("/adminInfo")
public class AdminInfoController extends BasePageController {

    @Autowired
    private AdminInfoService adminInfoService;

    @RequestMapping("/adminInfoList")
    public String adminInfoList(AdminInfo adminInfo, ModelMap modelMap){
        return getAdminInfoList(adminInfo,modelMap);
    }

    private String getAdminInfoList(AdminInfo adminInfo, ModelMap modelMap){
        PageInfo<AdminInfo> pageInfo = adminInfoService.adminInfoPage(adminInfo);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",adminInfo);
        return "adminInfo/adminInfoList";
    }

    @RequestMapping("/adminInfoListForCheck")
    public String adminInfoListForCheck(AdminInfo adminInfo, String lookUpType, ModelMap modelMap){
        getAdminInfoList(adminInfo,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "adminInfo/adminInfoListForCheck";
    }

    @RequestMapping("/adminInfoCreator")
    public String adminInfoCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getAdminInfoViewer(null,modelMap);
        return "adminInfo/adminInfoViewForCreator";
    }

    @RequestMapping("/adminInfoCreator.do")
    public ModelAndView adminInfoCreator(HttpServletRequest request, AdminInfo adminInfo){
        String password = request.getParameter("pwd");
        if (!password.equals(adminInfo.getPassword())){
            return getErrorResponse("密码不一致");
        }
        if (StringUtils.isEmpty(adminInfo.getPassword())){
            return getErrorResponse("密码不能为空");
        }
        Integer result = adminInfoService.addAdminInfo(adminInfo);
        if (result > 0)
            return getSuccessResponseWithNewId(adminInfo.getGid());
        return getErrorResponse("添加系统管理用户信息失败");
    }

    @RequestMapping("/adminInfoEditor")
    public String adminInfoEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getAdminInfoViewer(gid,modelMap);
        return "adminInfo/adminInfoViewForEditor";
    }

    @RequestMapping("/adminInfoEditor.do")
    public ModelAndView adminInfoEditor(AdminInfo adminInfo, HttpServletRequest request){
        String password = request.getParameter("pwd");
        if (!password.equals(adminInfo.getPassword())){
            return getErrorResponse("密码不一致");
        }
        if (StringUtils.isEmpty(adminInfo.getPassword())){
            return getErrorResponse("密码不能为空");
        }
        Integer result = adminInfoService.updateAdminInfo(adminInfo);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改系统管理用户信息失败");
    }

    @RequestMapping("/adminInfoViewer")
    public String adminInfoViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getAdminInfoViewer(gid,modelMap);
    }

    public String getAdminInfoViewer(Long gid, ModelMap modelMap){
        AdminInfo adminInfo = null != gid ? adminInfoService.adminInfoDetail(gid) : new AdminInfo();
        modelMap.addAttribute("data",adminInfo);
        return "adminInfo/adminInfoViewForViewer";
    }

}
