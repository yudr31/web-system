package com.spring.boot.web.service;

import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.dto.ModelConfigQuicklyCreatorDTO;
import com.spring.boot.web.mapper.ModelConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * @author yuderen
 * @version 2018/9/12 13:50
 */
@Service
public class ModelConfigService extends BaseBeanService<ModelConfigMapper, ModelConfig> {

    @Autowired
    private ModelConfigMapper modelConfigMapper;

    public List<ModelConfig> findByModelTypeAndProject(Integer modelType, String belongProject){
        ModelConfig modelConfig = new ModelConfig();
        modelConfig.setModelType(modelType);
        modelConfig.setBelongProject(belongProject);
        return super.fetchRecordList(modelConfig);
    }

    @Transactional
    public Integer modelConfigQuicklyCreator(ModelConfigQuicklyCreatorDTO creatorDTO){
        int count = 0;
        ModelConfig modelConfig = creatorDTO.getModelConfig();
        for (ModelConfig config : creatorDTO.getModelConfigList()){
            ModelConfig model = fetchRecordByGid(config.getGid());
            config.setBelongProject(modelConfig.getBelongProject());
            config.setModelType(modelConfig.getModelType());
            config.setModelContent(model.getModelContent());
            count += insertSelective(config);
        }
        return count;
    }

    public List<ModelConfig> fetchModelConfigListByGidList(Long[] gids){
        return modelConfigMapper.fetchModelConfigListByGidList(Arrays.asList(gids));
    }

}
