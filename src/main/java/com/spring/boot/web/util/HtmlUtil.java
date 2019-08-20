package com.spring.boot.web.util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author yuderen
 * @version 2017/10/26 16:39
 */
public class HtmlUtil implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 2) return "";
        String method = String.valueOf(list.get(0));        //第一个参数为方法名
        String secondParam = String.valueOf(list.get(1));   //第二个参数
//        String value = String.valueOf(list.get(2));         //第三个参数
        StringBuffer stringBuffer = new StringBuffer();
        switch (method){
            case "convertMillisecondToLongDateString":
                return convertMillisecondToLongDateString(secondParam);
            case "convertMillisecondToShortDateString":
                return convertMillisecondToShortDateString(secondParam);
            case "formatLongDateString":
                return formatLongDateString(secondParam);
            default:
                return stringBuffer.toString();
        }
    }

    public String convertMillisecondToLongDateString(String second){
        if (!"null".equals(second) && StringUtils.isNotBlank(second)){
            return DateUtils.convertMillisecondToLongDateString(Long.parseLong(second));
        }
        return "";
    }

    public String convertMillisecondToShortDateString(String second){
        if (StringUtils.isNotBlank(second))
            return DateUtils.convertMillisecondToShortDateString(Long.parseLong(second));
        return "";
    }

    public String formatLongDateString(String second){
        if (StringUtils.isNotBlank(second)){
            String result = second.replace("T"," ");
            return result;
        }
        return "";
    }

}
