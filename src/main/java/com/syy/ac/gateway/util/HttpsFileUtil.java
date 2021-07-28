package com.syy.ac.gateway.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *  HttpClient实现HTTP文件通用下载类
 *  maven依赖
 *  可下载http文件、图片、压缩文件
 *  bug：获取response header中Content-Disposition中filename中文乱码问题
 *  @author TanGuozheng
 *
 */
public class HttpsFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpsFileUtil.class);
    public static final int cache = 10 * 1024;
    public static final boolean isWindows;
    public static final String splash;
    public static final String root;
    static {
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
            isWindows = true;
            splash = "\\";
            root="D:";
        } else {
            isWindows = false;
            splash = "/";
            root="/search";
        }
    }

    /**
     * 根据url下载文件，文件名从response header头中获取
     * @param url
     * @return
     */
    public static String download(String url) {
        return download(url,null, null);
    }

    /**
     * 通过路径和请求头下载地址
     * @param url   请求URL
     * @param headers   请求头
     * @return
     */
    public static String download(String url,Map<String,String> headers) {
        return download(url,headers, null);
    }


    /**
     * 根据url下载文件，保存到filepath中
     * @param url
     * @param filePath
     * @return
     */
    public static String download(String url, Map<String,String> headers, String filePath) {

        InputStream is = null;
        FileOutputStream fileOut = null;

        try {
            CloseableHttpClient client = HttpClientUtil.getHttpClient(url.startsWith("https://"));
            HttpGet httpget = new HttpGet(url);
            HttpClientUtil.setHeaderParams(httpget,headers);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            if (filePath == null)
                filePath = getFilePath(response);
            File file = new File(filePath);
            // mkdirs()文件夹不存在那么就会创建，存在则返回false
            if(file.getParentFile().mkdirs()){
                logger.info("======创建文件成功======");
            }else{
                logger.info("======文件已存在======");
            }
            fileOut = new FileOutputStream(file);
            is = entity.getContent();
            //根据实际运行效果 设置缓冲区大小
            byte[] buffer=new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileOut.write(buffer,0,ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            assert is != null;
            try {
                is.close();
                fileOut.flush();
                fileOut.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 获取response要下载的文件的默认路径
     * @param response
     * @return
     */
    public static String getFilePath(HttpResponse response) {
        String filepath = root + splash;
        String filename = getFileName(response);

        if (filename != null) {
            filepath += filename;
        } else {
            filepath += getRandomFileName();
        }
        return filepath;
    }
    /**
     * 获取response header中Content-Disposition中的filename值
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
    /**
     * 获取随机文件名
     * @return 返回随机文件名
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }
    /**
     * 获取response header
     * @param response
     */
    public static void outHeaders(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i]);
        }
    }
    public static void main(String[] args) {
//		String url = "http://bbs.btwuji.com/job.php?action=download&pid=tpc&tid=320678&aid=216617";
        String url="https://iot-data-cloud.oss-cn-guangzhou.aliyuncs.com/video/V1/SystemToIOTOperationVideo.zip";
//		String filepath = "D:\\test\\a.torrent";
//        String filepath = "D:\\test\\SystemToIOTOperationVideo.zip";
//        HttpsFileUtil.download(url, filepath);
    }
}