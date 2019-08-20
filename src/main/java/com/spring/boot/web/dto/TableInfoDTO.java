package com.spring.boot.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储表结构的信息
 * @author yuderen
 *
 */
@Data
public class TableInfoDTO {
	
	private String tableName;								//表名: ecsys_orderMonitor_autoConfirmOrderRuleGroup
	private String tableComment;							//表注释
	private String priKey;									//主键
	private List<ColumnInfoDTO> columns = new ArrayList();		//所有字段的信息

}
