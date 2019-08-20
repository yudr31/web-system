package com.spring.boot.web.pattern;

import com.spring.boot.common.util.SpringContextUtil;
import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.service.ModelConfigService;
import com.spring.boot.web.util.FreeMarkerUtil;
import com.spring.boot.web.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 使用模板方法设计模式执行生成代码文件
 * @author: yuderen
 * @version: 1.0 2017-11-17 20:36
 */
public class GenerateFile {

    @Autowired
    private ModelConfigService modelConfigService;

    public void getTemplateList(){
        this.modelConfigService = SpringContextUtil.getBean(ModelConfigService.class);
    }

    public void generateFile(String belongProject, List<String> modelTypeList, Object object, String dirName){
        for (String modelTypeStr : modelTypeList){
            Integer modelType = Integer.parseInt(modelTypeStr);
            List<ModelConfig> modelList = modelConfigService.findByModelTypeAndProject(modelType, belongProject);
            for (ModelConfig model : modelList){
                String result = FreeMarkerUtil.getResult(object, model);
                String savePath = StringUtil.addFileSeparator(dirName, model.getSavePath()) + model.getFileName();
                savePath = FreeMarkerUtil.getContext(object, model.getModelName(), savePath);
                if (StringUtils.isNotBlank(result)){
                    FreeMarkerUtil.generateFile(savePath, result);
                }
            }
        }
    }

    public final void process(String belongProject, List<String> modelTypeList, Object object, String dirName){

        this.getTemplateList();     // 获取碎片模板列表

        this.generateFile(belongProject, modelTypeList, object, dirName);  // 生成代码文件信息
    }

}
