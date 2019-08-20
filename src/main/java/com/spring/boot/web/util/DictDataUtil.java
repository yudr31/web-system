package com.spring.boot.web.util;

import com.spring.boot.common.util.SpringContextUtil;
import com.spring.boot.feign.pojo.web.DataDict;
import com.spring.boot.web.service.DataDictService;
import com.spring.boot.web.service.DictTypeService;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: yuderen
 * @version: 1.0 2017-9-17 19:59
 */
public class DictDataUtil implements TemplateMethodModelEx {

    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private DictTypeService dictTypeService;

    @Override
    public Object exec(List list) throws TemplateModelException {
        dataDictService = SpringContextUtil.getBean(DataDictService.class);
        if (list.size() < 3) return "";
        String method = String.valueOf(list.get(0));    //第一个参数为方法名
        String dictType = String.valueOf(list.get(1));  //第二个参数为字典类型
        String value = String.valueOf(list.get(2));     //第三个参数为当前值
        List<DataDict> dictList;
        if (StringUtils.isNotEmpty(dictType)){
            dictList = dataDictService.fetchDataDictListByType(dictType);
        } else {
            dictList = dataDictService.fetchDataDictListGroupDictType();
        }
        StringBuffer stringBuffer = new StringBuffer();
        switch (method){
            case "getDictLabelByValue":
                return getDictLabelByValue(dictList,value);
            case "getOptions":
                return getOptions(stringBuffer,dictList,value);
            case "getOptionsWithNull":
                return getOptionsWithNull(stringBuffer,dictList,value);
            case "getDictTypeOptions":
                return getDictTypeOptions(stringBuffer, dictList, value);
            case "getDictTypeOptionsWithNull":
                return getDictTypeOptionsWithNull(stringBuffer, dictList, value);
            default:
                return stringBuffer.toString();
        }
    }

    public String getDictLabelByValue(List<DataDict> dictList,String value){
        if (null != value && !"".equals(value)){
            for (DataDict dataDict : dictList){
                if (value.equals(dataDict.getDictValue())){
                    return dataDict.getDictLabel();
                }
            }
        }
        return "";
    }

    public String getDictTypeOptions(StringBuffer stringBuffer,List<DataDict> dictList,String value){
        value = StringUtils.isEmpty(value) ? "" : value;
        for (DataDict dataDict : dictList){
            String selected = value.equals(dataDict.getDictValue()) ? "selected" : "";
            stringBuffer.append("<option value='"+dataDict.getDictValue()+"' "+selected+">")
                    .append(dataDict.getDictLabel())
                    .append("</option>");
        }
        return stringBuffer.toString();
    }

    public String getDictTypeOptionsWithNull(StringBuffer stringBuffer,List<DataDict> dictList,String value){
        stringBuffer.append("<option value=''>所有</option>");
        getDictTypeOptions(stringBuffer,dictList,value);
        return stringBuffer.toString();
    }

    public String getOptions(StringBuffer stringBuffer,List<DataDict> dictList,String value){
        value = StringUtils.isEmpty(value) ? "" : value;
        for (DataDict dataDict : dictList){
            String selected = value.equals(dataDict.getDictValue()) ? "selected" : "";
            stringBuffer.append("<option value='"+dataDict.getDictValue()+"' "+selected+">")
                    .append(dataDict.getDictLabel())
                    .append("</option>");
        }
        return stringBuffer.toString();
    }

    public String getOptionsWithNull(StringBuffer stringBuffer,List<DataDict> dictList,String value){
        stringBuffer.append("<option value=''>所有</option>");
        getOptions(stringBuffer,dictList,value);
        String result = stringBuffer.toString();
        return result;
    }

}
