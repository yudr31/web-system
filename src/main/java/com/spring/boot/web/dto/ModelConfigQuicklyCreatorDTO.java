package com.spring.boot.web.dto;

import com.spring.boot.feign.pojo.web.ModelConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2019/7/16 18:07
 */
@Data
public class ModelConfigQuicklyCreatorDTO {


    private ModelConfig modelConfig;

    private List<ModelConfig> modelConfigList = new ArrayList();

}
