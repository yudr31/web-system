package com.spring.boot.web.dto;

import lombok.Data;

/**
 * @author yuderen
 * @version 2017/10/31 15:27
 */
@Data
public class NnkTableInfoDTO extends TableInfoDTO {

    private String moduleName;			// 表模块名：orderMonitor
    private String entityName;			// 实体类名
    private String protoEntity;			// 协议实体名
    private String infoName;			// 实体信息简称

    private String belongProject;		// 所属项目

}
