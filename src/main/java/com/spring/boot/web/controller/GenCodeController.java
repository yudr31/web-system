package com.spring.boot.web.controller;

import com.spring.boot.common.bean.BasePageController;
import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.dto.MasterSlaveModelDTO;
import com.spring.boot.web.dto.SingleTableInfoDTO;
import com.spring.boot.web.dto.TableInfoDTO;
import com.spring.boot.web.service.GenCodeService;
import com.spring.boot.web.util.FileUtil;
import com.spring.boot.web.util.FreeMarkerUtil;
import com.spring.boot.web.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yuderen
 * @version 2018/9/12 15:19
 */
@Slf4j
@Controller
@RequestMapping("/genCode")
public class GenCodeController  extends BasePageController {

    private static Logger logger = LoggerFactory.getLogger(GenCodeController.class);

    @Autowired
    private GenCodeService genCodeService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        TableInfoDTO tableInfo = new TableInfoDTO();
        modelMap.addAttribute("tableInfo", tableInfo);
        return "genCode/index";
    }

    @RequestMapping("/querySingleTable")
    public String querySingleTable(SingleTableInfoDTO tableInfo, ModelMap modelMap){
        genCodeService.querySingleTable(tableInfo);
        modelMap.addAttribute("tableInfo", tableInfo);
        return "genCode/singleModel/singleTable";
    }

    @RequestMapping("/generateSingleTable")
    public ModelAndView generateSingleTable(SingleTableInfoDTO tableInfo){
        genCodeService.generateSingleTable(tableInfo);
        return getSuccessResponse(tableInfo.getEntityName());
    }

    @RequestMapping("/previewSingleTable")
    public String previewSingleTable(SingleTableInfoDTO tableInfo, ModelMap modelMap) {
        Map result = genCodeService.previewSingleTable(tableInfo);
        modelMap.addAttribute("result", result);
        return "genCode/preview";
    }

    @RequestMapping("/queryMasterSlaveTable")
    public String queryMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel, ModelMap modelMap){
        genCodeService.queryMasterSlaveTable(masterSlaveModel);
        modelMap.addAttribute("masterSlave",masterSlaveModel);
        return "genCode/masterSlave/masterSlaveTable";
    }

    @RequestMapping("/generateMasterSlaveTable")
    public ModelAndView generateMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel){
        genCodeService.generateMasterSlaveTable(masterSlaveModel);
        return getSuccessResponse();
    }

    @RequestMapping("/previewMasterSlaveTable")
    public String previewMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel, ModelMap modelMap){
        Map result = genCodeService.previewMasterSlaveTable(masterSlaveModel);
        modelMap.addAttribute("result", result);
        return "genCode/preview";
    }

    @ResponseBody
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        String fileName = request.getParameter("fileName");
        String downloadPath = request.getSession().getServletContext().getRealPath("/download");
        FileUtil.zipAndDownload(response,downloadPath + "/" + fileName,fileName);
    }

    @RequestMapping("/previewGenerate")
    public ModelAndView previewGenerate(ModelConfig model){
        String filePath = StringUtil.addFileSeparator(model.getSavePath()) + model.getFileName();
        FreeMarkerUtil.generateFile(model.getFileName(),model.getModelContent());
        return getSuccessResponse(model.getFileName());
    }

    @ResponseBody
    @RequestMapping("/downloadPreviewFile")
    public void downloadPreviewFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = request.getParameter("fileName");
        String downloadPath = request.getSession().getServletContext().getRealPath("/download");
        FileUtil.downloadFile(response,StringUtil.addFileSeparator(downloadPath) + fileName,fileName);
    }

}
