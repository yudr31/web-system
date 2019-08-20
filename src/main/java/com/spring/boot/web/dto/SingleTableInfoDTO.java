package com.spring.boot.web.dto;


import com.spring.boot.feign.pojo.web.ModelConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2017/10/26 9:09
 */
@Data
public class SingleTableInfoDTO extends NnkTableInfoDTO {

    private String sqlFile;				// sql文件名
    private String protoFile;			// protobuf协议文件名
    private String modulePkg;			// 模块包名

    private Long preview;				// 预览的文件
    private boolean batchAdd;           // 是否生成批量添加代码
    private List<String> moduleType = new ArrayList();
    private List<ModelConfig> modelList = new ArrayList();		//模板列表

}
