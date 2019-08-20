package com.spring.boot.web.service;

import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.dto.*;
import com.spring.boot.web.pattern.GenerateFile;
import com.spring.boot.web.util.DataBaseUtil;
import com.spring.boot.web.util.FreeMarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuderen
 * @version 2018/9/12 15:20
 */
@Service
public class GenCodeService {

    @Autowired
    private ModelConfigService modelConfigService;

    public void querySingleTable(SingleTableInfoDTO tableInfo){
        Integer modelType = Integer.parseInt(tableInfo.getModuleType().get(0));
        String project = tableInfo.getBelongProject();
        List<ModelConfig> modelList = modelConfigService.findByModelTypeAndProject(modelType,project);
        tableInfo.setModelList(modelList);
        DataBaseUtil.getTableInfo(tableInfo, project);
    }

    public void queryMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel){
        Integer modelType = Integer.parseInt(masterSlaveModel.getMaster().getModuleType().get(0));
        String project = masterSlaveModel.getMaster().getBelongProject();
        List<ModelConfig> modelList = modelConfigService.findByModelTypeAndProject(modelType,project);
        masterSlaveModel.getMaster().setModelList(modelList);
        DataBaseUtil.getTableInfo(masterSlaveModel.getMaster(), project);
        for (SlaveTableInfoDTO tableInfo : masterSlaveModel.getSlave()){
            DataBaseUtil.getTableInfo(tableInfo, project);
        }
    }

    public boolean generateSingleTable(SingleTableInfoDTO tableInfo){
        initEnumerOrAmount(tableInfo);
        String belongProject = tableInfo.getBelongProject();
        List<String> modelTypeList = tableInfo.getModuleType();
        new GenerateFile().process(belongProject, modelTypeList, tableInfo, tableInfo.getEntityName());
        return true;
    }

    public Map previewSingleTable(SingleTableInfoDTO tableInfo){
        Map result = new HashMap();
        initEnumerOrAmount(tableInfo);
        ModelConfig model = modelConfigService.fetchRecordByGid(tableInfo.getPreview());
        model.setSavePath(FreeMarkerUtil.getContext(tableInfo,model.getModelName(),model.getSavePath()));
        model.setFileName(FreeMarkerUtil.getContext(tableInfo,model.getModelName(),model.getFileName()));
        result.put("content",FreeMarkerUtil.getResult(tableInfo,model));
        result.put("model",model);
        return result;
    }

    public boolean generateMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel){
        initEnumerOrAmount(masterSlaveModel.getMaster());
        for (SlaveTableInfoDTO tableInfo : masterSlaveModel.getSlave()){
            initEnumerOrAmount(tableInfo);
        }
        String belongProject = masterSlaveModel.getMaster().getBelongProject();
        List<String> modelTypeList = masterSlaveModel.getMaster().getModuleType();
        new GenerateFile().process(belongProject, modelTypeList, masterSlaveModel,masterSlaveModel.getMaster().getEntityName());
        return true;
    }

    public Map previewMasterSlaveTable(MasterSlaveModelDTO masterSlaveModel){
        Map result = new HashMap();
        initEnumerOrAmount(masterSlaveModel.getMaster());
        for (SlaveTableInfoDTO tableInfo : masterSlaveModel.getSlave()){
            initEnumerOrAmount(tableInfo);
        }
        ModelConfig model = modelConfigService.fetchRecordByGid(masterSlaveModel.getMaster().getPreview());
        model.setSavePath(FreeMarkerUtil.getContext(masterSlaveModel,model.getModelName(),model.getSavePath()));
        model.setFileName(FreeMarkerUtil.getContext(masterSlaveModel,model.getModelName(),model.getFileName()));
        result.put("content",FreeMarkerUtil.getResult(masterSlaveModel,model));
        result.put("model",model);
        return result;
    }

    public void initEnumerOrAmount(TableInfoDTO tableInfo){
        for (ColumnInfoDTO columnInfo : tableInfo.getColumns()){
            if ("select".equals(columnInfo.getTag())){
                columnInfo.setEnumBool(true);
            } else if ("amount".equals(columnInfo.getTag())){
                columnInfo.setAmountBool(true);
            } else if ("dict".equals(columnInfo.getTag())){
                columnInfo.setDictBool(true);
            }
        }
    }

}
