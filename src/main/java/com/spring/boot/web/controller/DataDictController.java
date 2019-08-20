package com.spring.boot.web.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.web.DataDict;
import com.spring.boot.feign.pojo.web.DictType;
import com.spring.boot.web.service.DataDictService;
import com.spring.boot.web.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/12 14:11
 */
@Controller
@RequestMapping("/dataDict")
public class DataDictController extends BasePageController {

    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private DictTypeService dictTypeService;

    @RequestMapping("/dataDictList")
    public String dataDictList(DataDict dataDict, ModelMap modelMap){
        return getDataDictList(dataDict, modelMap);
    }

    private String getDataDictList(DataDict dataDict, ModelMap modelMap){
        PageInfo<DataDict> pageInfo = dataDictService.fetchRecordPageInfo(dataDict);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",dataDict);
        return "dataDict/dataDictList";
    }

    @RequestMapping("/dataDictListForCheck")
    public String dictTypeListForCheck(DataDict dataDict, String lookUpType, ModelMap modelMap){
        getDataDictList(dataDict, modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "dataDict/dataDictListForCheck";
    }

    @RequestMapping("/dataDictCreator")
    public String dataDictCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getDataDictViewer(null,modelMap);
        return "dataDict/dataDictViewForCreator";
    }

    @RequestMapping("/dataDictCreator.do")
    public ModelAndView dataDictCreator(DataDict dataDict){
        Integer result = dataDictService.insertSelective(dataDict);
        if (result > 0)
            return getSuccessResponseWithNewId(dataDict.getGid());
        return getErrorResponse("添加数据字典失败");
    }

    @RequestMapping("/dataDictEditor")
    public String dataDictEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getDataDictViewer(gid,modelMap);
        return "dataDict/dataDictViewForEditor";
    }

    @RequestMapping("/dataDictEditor.do")
    public ModelAndView dataDictEditor(DataDict dataDict){
        Integer result = dataDictService.updateSelectiveByKey(dataDict);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改数据字典失败");
    }

    @RequestMapping("/dataDictDeleter.do")
    public ModelAndView dataDictDeleter(@RequestParam("gids") List<Long> gidList){
        Integer result = dataDictService.batchRemove(gidList);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("删除数据字典失败");
    }

    @RequestMapping("/dataDictViewer")
    public String dataDictViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getDataDictViewer(gid,modelMap);
    }

    public String getDataDictViewer(Long gid, ModelMap modelMap){
        DataDict dataDict = null != gid ? dataDictService.fetchRecordByGid(gid) : new DataDict();
        modelMap.addAttribute("data",dataDict);
        return "dataDict/dataDictViewForViewer";
    }

    @RequestMapping("/dictTypeList")
    public String dictTypeList(DictType dictType, ModelMap modelMap){
        return getDictTypeList(dictType,modelMap);
    }

    public String getDictTypeList(DictType dictType, ModelMap modelMap){
        PageInfo<DictType> pageInfo = dictTypeService.fetchRecordPageInfo(dictType);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("param",dictType);
        return "dictType/dictTypeList";
    }

    @RequestMapping("/dictTypeListForCheck")
    public String dictTypeListForCheck(DictType dictType, String lookUpType, ModelMap modelMap){
        getDictTypeList(dictType,modelMap);
        modelMap.addAttribute("lookUpType", lookUpType);
        return "dictType/dictTypeListForCheck";
    }

    @RequestMapping("/dictTypeCreator")
    public String dictTypeCreator(ModelMap modelMap){
        setPageModeForCreator(modelMap);
        getDictTypeViewer(null,modelMap);
        return "dictType/dictTypeViewForCreator";
    }

    @RequestMapping("/dictTypeCreator.do")
    public ModelAndView dictTypeCreator(DictType dictType){
        Integer result = dictTypeService.insertSelective(dictType);
        if (result > 0)
            return getSuccessResponseWithNewId(dictType.getGid());
        return getErrorResponse("添加数据字典失败");
    }

    @RequestMapping("/dictTypeEditor")
    public String dictTypeEditor(Long gid, ModelMap modelMap){
        setPageModeForEditor(modelMap);
        getDictTypeViewer(gid, modelMap);
        return "dictType/dictTypeViewForEditor";
    }

    @RequestMapping("/dictTypeEditor.do")
    public ModelAndView dictTypeEditor(DictType dictType){
        Integer result = dictTypeService.updateSelectiveByKey(dictType);
        if (result > 0)
            return getSuccessResponse();
        return getErrorResponse("修改数据字典失败");
    }

    @RequestMapping("/dictTypeViewer")
    public String dictTypeViewer(Long gid, ModelMap modelMap){
        setPageModeForViewer(modelMap);
        return getDictTypeViewer(gid, modelMap);
    }

    public String getDictTypeViewer(Long gid, ModelMap modelMap){
        DictType dictType = null != gid ? dictTypeService.fetchRecordByGid(gid) : new DictType();
        modelMap.addAttribute("data",dictType);
        return "dictType/dictTypeViewForViewer";
    }

}
