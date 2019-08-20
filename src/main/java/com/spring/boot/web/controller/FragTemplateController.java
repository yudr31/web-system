package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.web.FragmentTemplate;
import com.spring.boot.web.service.FragmentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author yuderen
 * @version 2018/9/12 10:43
 */
@Controller
@RequestMapping("/fragmentTemplate")
public class FragTemplateController extends BasePageController {

    @Autowired
    private FragmentTemplateService fragmentTemplateService;

    @RequestMapping("/fragmentTemplateList")
    public String fragmentTemplateList(FragmentTemplate fragmentTemplate, ModelMap modelMap){
        return getFragmentTemplateList(fragmentTemplate, modelMap);
    }

    private String getFragmentTemplateList(FragmentTemplate fragmentTemplate, ModelMap modelMap){
        PageInfo<FragmentTemplate> pageInfo = fragmentTemplateService.fetchRecordPageInfo(fragmentTemplate);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",fragmentTemplate);
        return "fragmentTemplate/fragmentTemplateList";
    }

    @RequestMapping("/fragmentTemplateForCheck")
    public String fragmentTemplateListForCheck(FragmentTemplate fragmentTemplate, String lookUpType, ModelMap modelMap){
        getFragmentTemplateList(fragmentTemplate,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "fragmentTemplate/fragmentTemplateForCheck";
    }

    @RequestMapping("/fragmentTemplateCreator")
    public String fragmentTemplateCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getFragmentTemplateViewer(null,modelMap);
        return "fragmentTemplate/fragmentTemplateViewForCreator";
    }

    @RequestMapping("/fragmentTemplateCreator.do")
    public ModelAndView fragmentTemplateCreator(FragmentTemplate fragmentTemplate){
        Integer result = fragmentTemplateService.insertSelective(fragmentTemplate);
        if (result > 0)
            return getSuccessResponseWithNewId(fragmentTemplate.getGid());
        return getErrorResponse("添加数据字典失败");
    }

    @RequestMapping("/fragmentTemplateEditor")
    public String fragmentTemplateEditor(Long id, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getFragmentTemplateViewer(id,modelMap);
        return "fragmentTemplate/fragmentTemplateViewForEditor";
    }

    @RequestMapping("/fragmentTemplateEditor.do")
    public ModelAndView fragmentTemplateEditor(FragmentTemplate fragmentTemplate){
        Integer result = fragmentTemplateService.updateSelectiveByKey(fragmentTemplate);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改数据字典失败");
    }

    @RequestMapping("/fragmentTemplateViewer")
    public String fragmentTemplateViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getFragmentTemplateViewer(gid,modelMap);
    }

    public String getFragmentTemplateViewer(Long gid, ModelMap modelMap){
        FragmentTemplate fragmentTemplate = null != gid ? fragmentTemplateService.fetchRecordByGid(gid) : new FragmentTemplate();
        modelMap.addAttribute("data",fragmentTemplate);
        return "fragmentTemplate/fragmentTemplateViewForViewer";
    }

}
