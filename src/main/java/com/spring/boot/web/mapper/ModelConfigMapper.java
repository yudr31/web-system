package com.spring.boot.web.mapper;

import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.web.ModelConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yuderen
 * @version 2018/9/12 13:50
 */
public interface ModelConfigMapper extends BaseBeanMapper<ModelConfig> {

    List<ModelConfig> fetchModelConfigListByGidList(@Param("gidList") List<Long> gidList);

}
