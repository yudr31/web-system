package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.web.MappingField;
import com.spring.boot.web.service.MappingFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author yuderen
 * @version 2019-6-6 15:46:11
 */
@Controller
@RequestMapping("/mappingField")
public class MappingFieldController extends BasePageController {

    @Autowired
    private MappingFieldService mappingFieldService;

    @RequestMapping("/mappingFieldList")
    public String mappingFieldList(MappingField mappingField, ModelMap modelMap){
        return getMappingFieldList(mappingField, modelMap);
    }

    private String getMappingFieldList(MappingField mappingField, ModelMap modelMap){
        PageInfo<MappingField> pageInfo = mappingFieldService.fetchRecordPageInfo(mappingField);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",mappingField);
        return "mappingField/mappingFieldList";
    }

    @RequestMapping("/mappingFieldListForCheck")
    public String mappingFieldListForCheck(MappingField mappingField, String lookUpType, ModelMap modelMap){
        getMappingFieldList(mappingField, modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "mappingField/mappingFieldListForCheck";
    }

    @RequestMapping("/mappingFieldCreator")
    public String mappingFieldCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getMappingFieldViewer(null,modelMap);
        return "mappingField/mappingFieldViewForCreator";
    }

    @RequestMapping("/mappingFieldCreator.do")
    public ModelAndView mappingFieldCreator(MappingField mappingField){
        Integer result = mappingFieldService.insertSelective(mappingField);
        if (result > 0)
            return getSuccessResponseWithNewId(mappingField.getGid());
        return getErrorResponse("添加属性映射信息失败");
    }

    @RequestMapping("/mappingFieldEditor")
    public String mappingFieldEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getMappingFieldViewer(gid,modelMap);
        return "mappingField/mappingFieldViewForEditor";
    }

    @RequestMapping("/mappingFieldEditor.do")
    public ModelAndView mappingFieldEditor(MappingField mappingField){
        Integer result = mappingFieldService.updateSelectiveByKey(mappingField);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改属性映射信息失败");
    }

    @RequestMapping("/mappingFieldViewer")
    public String mappingFieldViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getMappingFieldViewer(gid,modelMap);
    }

    public String getMappingFieldViewer(Long gid, ModelMap modelMap){
        MappingField mappingField = null != gid ? mappingFieldService.fetchRecordByGid(gid) : new MappingField();
        modelMap.addAttribute("data",mappingField);
        return "mappingField/mappingFieldViewForViewer";
    }

}
