package com.spring.boot.web.util;





import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author: yuderen
 * @version: 1.0 2017-11-4 14:34
 */
@Slf4j
public class FileUtil {

    /**
     * 将打包好的文件下载到用户浏览器
     * @param response
     * @param addr
     * @param fileName
     */
    public static void downloadFile(HttpServletResponse response, String addr, String fileName){
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((fileName).getBytes("GBK"), "ISO-8859-1"));
            OutputStream os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(addr)), 1024*1024*100);
            int b = -1;
            while ((b = bis.read()) != -1) {
                os.write(b);
            }
            os.flush();
            os.close();
            os = null;
            bis.close();
            bis = null;
        } catch (Exception e) {
            log.error("下载或压缩出现错误");
        }
        log.info("*****************下载成功*******************");
//        deleteFile(addr);
//        System.out.println("*****************下载成功*******************");
    }

    /**
     * 将打包好的文件下载到用户浏览器
     * @param response
     * @param addr
     * @param fileName
     */
    public static void zipAndDownload(HttpServletResponse response, String addr, String fileName){
        if (!fileName.endsWith(".zip")) fileName = fileName +".zip";
        try {
            FileUtil.zipFiles(fileName,addr);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((fileName).getBytes("GBK"), "ISO-8859-1"));
            OutputStream os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)), 1024*1024*100);
            int b = -1;
            while ((b = bis.read()) != -1) {
                os.write(b);
            }
            os.flush();
            os.close();
            os = null;
            bis.close();
            bis = null;
        } catch (Exception e) {
            System.out.println("下载债权包出现错误");
        }
        FileUtil.delFile(fileName);
        FileUtil.deleteDirectory(addr);
        log.info("*****************压缩并下载成功*******************");
    }

    /**
     * 压缩文件-由于out要在递归调用外,所以封装一个方法用来
     * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles)
     * @param zipPath 压缩后文件路径及名字
     * @param filesPath 被压缩文件路径数组
     * @throws IOException
     */
    public static void zipFiles(String zipPath,String... filesPath){
        File zip = new File(zipPath);
        File[] files = new File[filesPath.length];
        for(int i=0;i<filesPath.length;i++){
            files[i] = new File(filesPath[i]);
        }
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
            zipFiles(out,"",files);
            out.close();
        } catch (Exception e) {
            log.error("读取文件或关闭资源出现错误！");
//            System.out.println("读取文件或关闭资源出现错误！");
        }
        log.info("*****************压缩成功*******************");
//        System.out.println("*****************压缩成功*******************");
    }

    /**
     * 压缩文件-File
	 * @param path 被压缩文件路径
	 * @param srcFiles 被压缩源文件
	 */
    public static void zipFiles(ZipOutputStream out, String path, File... srcFiles){
        path = path.replaceAll("\\*", "/");
        if(!"".equals(path)&&!path.endsWith("/")){
            path+="/";
        }
        byte[] buf = new byte[1024];
        try {
            for(int i=0;i<srcFiles.length;i++){
                if(srcFiles[i].isDirectory()){
                    File[] files = srcFiles[i].listFiles();
                    String srcPath = srcFiles[i].getName();
                    srcPath = srcPath.replaceAll("\\*", "/");
                    if(!srcPath.endsWith("/")){
                        srcPath+="/";
                    }
                    out.putNextEntry(new ZipEntry(path+srcPath));
                    zipFiles(out,path+srcPath,files);
                }
                else{
                    FileInputStream in = new FileInputStream(srcFiles[i]);
                    System.out.println(path + srcFiles[i].getName());
                    out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
                    int len;
                    while((len=in.read(buf))>0){
                        out.write(buf,0,len);
                    }
                    out.closeEntry();
                    in.close();
                }
            }
        } catch (Exception e) {
            log.error("压缩文件出现错误！");
            e.printStackTrace();
        }
    }

    /**
     * 解压文件
     * @param filePath  被压缩文件路径
     * @throws IOException
     */
    public static void unzipFile(String filePath) throws IOException {
        File file = new File(filePath);
        filePath = filePath.replace(file.getName(), "");
        ZipFile zipFile = new ZipFile(file);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));

        ZipEntry entry = null;
        while ((entry = zis.getNextEntry()) != null) {
            File outFile = new File(filePath + entry.getName());
            if(entry.isDirectory()){
                if(!outFile.exists())
                    outFile.mkdir();
            }else{
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
                byte[] b = new byte[1024*4096];
                while (true) {
                    int len = bis.read(b);
                    if (len == -1) break;
                    bos.write(b, 0, len);
                }
                bis.close();
                bos.close();
            }
        }
        zis.close();
        if(null != zipFile)
            zipFile.close();
        file.deleteOnExit();
    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("删除文件 " + fileName + " 成功!");
                return true;
            } else {
                log.debug("删除文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    /**
     * 删除目录及目录下的文件
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        if (!deleteDirChildFiles(dirFile)) {
            log.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            log.debug("删除目录 " + dirName + " 成功!");
            return true;
        } else {
            log.debug("删除目录 " + dirName + " 失败!");
            return false;
        }
    }

    /**
     *
     * 删除目录下的所有文件
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectoryFiles(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        if (!deleteDirChildFiles(dirFile)) {
            log.debug("删除目录失败!");
            return false;
        }
        return true;
    }

    private static boolean deleteDirChildFiles(File dirFile){
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }
        return flag;
    }

}
