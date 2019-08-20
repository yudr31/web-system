package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.dto.ModelConfigQuicklyCreatorDTO;
import com.spring.boot.web.service.ModelConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/12 13:49
 */
@Slf4j
@Controller
@RequestMapping("/modelConfig")
public class ModelConfigController extends BasePageController {

    @Autowired
    private ModelConfigService modelConfigService;

    @RequestMapping("/modelConfigList")
    public String modelConfigList(ModelConfig modelConfig, ModelMap modelMap){
        log.error("test error");
        log.info("test info");
        log.debug("test debug");
        return getModelConfigList(modelConfig, modelMap);
    }

    private String getModelConfigList(ModelConfig modelConfig, ModelMap modelMap){
        PageInfo<ModelConfig> pageInfo = modelConfigService.fetchRecordPageInfo(modelConfig);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",modelConfig);
        return "modelConfig/modelConfigList";
    }

    @RequestMapping("/modelConfigForCheck")
    public String modelConfigListForCheck(ModelConfig modelConfig, String lookUpType, ModelMap modelMap){
        getModelConfigList(modelConfig, modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "modelConfig/modelConfigForCheck";
    }

    @RequestMapping("/modelConfigCreator")
    public String modelConfigCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getModelConfigViewer(null,modelMap);
        return "modelConfig/modelConfigViewForCreator";
    }

    @RequestMapping("/modelConfigCreator.do")
    public ModelAndView modelConfigCreator(ModelConfig modelConfig){
        Integer result = modelConfigService.insertSelective(modelConfig);
        if (result > 0)
            return getSuccessResponseWithNewId(modelConfig.getGid());
        return getErrorResponse("添加数据字典失败");
    }

    @RequestMapping("/modelConfigQuicklyCreator")
    public String modelConfigQuicklyCreator(@RequestParam("gids") Long[] gids, ModelMap modelMap){
        List<ModelConfig> resultList = modelConfigService.fetchModelConfigListByGidList(gids);
        modelMap.addAttribute("resultList", resultList);
        modelMap.addAttribute("param", new ModelConfig());
        setPageModeForCreator(modelMap);
        getModelConfigViewer(null, modelMap);
        return "modelConfig/modelConfigViewForQuicklyCreator";
    }

    @RequestMapping("/modelConfigQuicklyCreator.do")
    public String modelConfigQuicklyCreator(ModelConfigQuicklyCreatorDTO creatorDTO, ModelMap modelMap){
        Integer result = modelConfigService.modelConfigQuicklyCreator(creatorDTO);
        return getModelConfigList(creatorDTO.getModelConfig(), modelMap);
    }

    @RequestMapping("/modelConfigEditor")
    public String modelConfigEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getModelConfigViewer(gid,modelMap);
        return "modelConfig/modelConfigViewForEditor";
    }

    @RequestMapping("/modelConfigEditor.do")
    public ModelAndView modelConfigEditor(ModelConfig modelConfig){
        Integer result = modelConfigService.updateSelectiveByKey(modelConfig);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改数据字典失败");
    }

    @RequestMapping("/modelConfigViewer")
    public String modelConfigViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getModelConfigViewer(gid,modelMap);
    }

    public String getModelConfigViewer(Long gid, ModelMap modelMap){
        ModelConfig modelConfig = null != gid ? modelConfigService.fetchRecordByGid(gid) : new ModelConfig();
        modelMap.addAttribute("data",modelConfig);
        return "modelConfig/modelConfigViewForViewer";
    }

    @RequestMapping("/modelConfigDeleter.do")
    public ModelAndView modelConfigDeleter(Long[] gids){
        Integer result = modelConfigService.batchRemove(Arrays.asList(gids));
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("删除数据字典失败");
    }

}
