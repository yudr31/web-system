package com.spring.boot.web.util;

import com.spring.boot.common.util.SpringContextUtil;
import com.spring.boot.feign.pojo.web.FragmentTemplate;
import com.spring.boot.feign.pojo.web.ModelConfig;
import com.spring.boot.web.service.FragmentTemplateService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yuderen
 * @version: 1.0 2017-9-21 22:53
 */
public class FreeMarkerUtil {

    private static FragmentTemplateService fragmentTemplateService;

    public static String getResult(Object tableInfo, ModelConfig model){
        return getContext(tableInfo,model.getModelName(),model.getModelContent());
    }

    public static String getContext(Object tableInfo,String modelName,String modelContext){
        if (null == fragmentTemplateService){
            fragmentTemplateService = SpringContextUtil.getBean(FragmentTemplateService.class);
        }
        List<FragmentTemplate> fragmentTemplateList = fragmentTemplateService.fetchRecordList(null);
        Configuration tempConfiguration = new Configuration(Configuration.getVersion());
        Map<String, Object> root = new HashMap();
        StringWriter result = new StringWriter();
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(modelName,modelContext);
        for (FragmentTemplate template : fragmentTemplateList){
            loader.putTemplate(template.getModelName(),template.getModelContent());
        }
        tempConfiguration.setTemplateLoader(loader);
        root.put("tableInfo", tableInfo);
        try {
            Template template = tempConfiguration.getTemplate(modelName,"utf-8");
            template.process(root,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static boolean generateFile(String savePath, String content) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String downloadPath = request.getSession().getServletContext().getRealPath("/download");
        savePath = StringUtil.addFileSeparator(downloadPath) + savePath;
        try {
            String fileStr = File.separator;
            savePath = savePath.replace("\\", fileStr);
            savePath = savePath.replace("\\\\", fileStr);
            savePath = savePath.replace("/", fileStr);
            savePath = savePath.replace("//", fileStr);
            System.out.println("生成文件：" + savePath);
            int temp = savePath.lastIndexOf(fileStr);

            String tem = savePath.substring(0, temp + 1);
            File file = new File(tem);
            if (!(file.exists())) {
                file.mkdirs();
            }
            File file1 = new File(savePath);
            file1.createNewFile();

            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(savePath), "utf-8")));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
