package com.spring.boot.web.util;

import com.spring.boot.common.util.SpringContextUtil;
import com.spring.boot.feign.pojo.web.MappingField;
import com.spring.boot.web.service.MappingFieldService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class StringUtil {

	private static MappingFieldService mappingFieldService;

	/**
	 * 通过表字段属性类型获取实体对应的属性类型
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public static String getPropertyType(String dataType, String precision, String scale, String belongProject) {
		if (null == mappingFieldService){
			mappingFieldService = SpringContextUtil.getBean(MappingFieldService.class);
		}
		MappingField mappingField = new MappingField();
		mappingField.setBelongProject("all_project");
        List<MappingField> allMappingFieldList = mappingFieldService.fetchRecordList(mappingField);
        mappingField.setBelongProject(belongProject);
        List<MappingField> projectMappingFieldList = mappingFieldService.fetchRecordList(mappingField);
        projectMappingFieldList.addAll(allMappingFieldList);
        for (MappingField mf : projectMappingFieldList){
            if ("equal".equals(mf.getMappingCondition())){
                if (dataType.equals(mf.getColumnType())){
                    return mf.getFieldType();
                }
            } else if ("contain".equals(mf.getMappingCondition())){
                if (dataType.contains(mf.getColumnType())){
                    return mf.getFieldType();
                }
            }
        }
		return dataType;
	}

	/**
	 * 获取proto协议类型
	 * @param dataType
	 * @return
	 */
	public static String getProtoType(String dataType){
		String protoType = "";
		if (dataType.contains("String")) {
			protoType = "string";
		} else if (dataType.contains("Long")) {
			protoType = "sint64";
		} else if(dataType.contains("Integer")){
			protoType = "sint32";
		} else if (dataType.contains("Double") || dataType.contains("decimal")) {
			protoType = "double";
		} else {
			protoType = "float";
		}
		return protoType;
	}

	/**
	 * 格式化表字段名对应实体属性名
	 * @param columnName
	 * @return
	 */
	public static String formatField(String columnName) {
		String[] arrayOfString = columnName.split("_");
		columnName = "";
		int i = 0;
		int length = arrayOfString.length;
		while (i < length) {
			if (i > 0) {
				String str = arrayOfString[i].toLowerCase();
				str = str.substring(0, 1).toUpperCase()
						+ str.substring(1);
				columnName = columnName + str;
			} else {
				columnName = columnName + arrayOfString[i].toLowerCase();
			}
			++i;
		}
		return columnName;
	}

	/**
	 * 将首字母和下划线后的字母转换为大写
	 * @param paramString
	 * @return
	 */
	public static String formatFieldCapital(String paramString) {
		String tempStr = paramString.indexOf("_") > -1 ? formatField(paramString) : paramString;
		String result = tempStr.substring(0, 1).toUpperCase()
				+ tempStr.substring(1);
		return result;
	}

	/**
	 * 将字符首字母转换为小写
	 * @param str
	 * @return
	 */
	public static String lowerCaseStringFirst(String str) {
		String b = str.replace(str.charAt(0), (char) (str.charAt(0) + ' '));
		return b;
	}

	/**
	 * 将字符串转成utf-8编码
	 * @param str
	 * @return
	 */
	public static String decodeString(String str){
		String decode = "";
		try {
			decode = new String(str.getBytes("iso-8859-1"),"utf-8");
		} catch (Exception e){
			e.printStackTrace();
		}
		return decode;
	}

	/**
	 * 给字符添加分隔符
	 * 判断字符串是否以文件分隔符（/）结束，否-则加上文件分隔符（/）
	 * @param strArr
	 * @return
	 */
	public static String addFileSeparator(String... strArr){
		String result = "";
		for (String str : strArr){
			if (StringUtils.isNotBlank(str) && !str.endsWith("/")){
				result += str + "/";
			} else {
				result += str;
			}

		}
		return result;
	}

}
