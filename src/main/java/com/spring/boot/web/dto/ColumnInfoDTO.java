package com.spring.boot.web.dto;

import lombok.Data;

/**
 * 封装表中一个字段的信息
 * @author yuderen
 *
 */
@Data
public class ColumnInfoDTO {
	
	private String columnName;		//表字段名称
	private String fieldName;		//实体类属性名
	private String columnType;		//字段的数据类型
	private String fieldType;		//实体类属性类型

	private String protoType;		//proto协议类型
	private String protoComment;	//协议注释
	private String columnComment;	//字段的注释
	private String tag;				//前端页面标签类型

	private boolean enumBool;		//是否是枚举类型
	private boolean amountBool;		//是否是金额
	private boolean dictBool;		//是否是字典
	private boolean searchColumn;	//是否是搜索项
	private boolean showColumn;		//是否是展示项

	private boolean batchColumn;	//是否是批量添加项
	private String enumType;		//枚举类型
	private String nullable;		//字段是否可为空
	private int keyType;			//字段的键类型(0:普通键，1：主键，2：外键)

}
