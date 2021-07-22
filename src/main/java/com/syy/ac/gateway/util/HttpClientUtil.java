package com.syy.ac.gateway.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: TanGuozheng
 * @Date: 2021年7月21日
 */
public class HttpClientUtil {

    private static String tokenString = "";

    /**
     * 以get方式调用第三方接口
     */
    public static String doGet(String url,Map<String,String> headerParams) {

        String responseContent = "";
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            //这里我加了一个是否需要创建一个https连接的判断
           httpClient = getHttpClient(url.startsWith("https://"));

            //2.生成get请求对象，并设置请求头信息
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("auth_token", tokenString);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            //3.执行请求
            response = httpClient.execute(httpGet);
            //4.处理响应信息
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseContent = EntityUtils.toString(response.getEntity(), "utf-8");
                return responseContent;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 以post方式调用第三方接口
     */
    public static String doPost(String url, boolean isHttps, JSONObject paramEntity) {
        String responseContent = "";
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = getHttpClient(isHttps);
            //2.生成post请求对象，并设置请求头信息
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("auth_token", tokenString);
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            //3.设置请求参数
            if (!Objects.isNull(paramEntity)) {
                String paramStr = JSONObject.toJSONString(paramEntity);
                StringEntity entity = new StringEntity(paramStr, ContentType.create("application/json", "utf-8"));
                httpPost.setEntity(entity);
            }
            //4.执行请求
            response = httpClient.execute(httpPost);
            //5.处理响应信息
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseContent = EntityUtils.toString(response.getEntity(), "utf-8");
                return responseContent;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static CloseableHttpClient getHttpClient(Boolean isHttps)  {
        if (isHttps) {
            //配置https请求的一些参数
            SSLContext sslContext = null;
            try {
                sslContext = SSLContextBuilder.create().setProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
                e.printStackTrace();
            }
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            return HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
        } else {
            return HttpClientBuilder.create().build();
        }
    }

    public static String doPostFile(String url, Map<String, String> param,Map<String, String> header, File file) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 处理https链接
        if (url.startsWith("https://")) {
            httpClient = getHttpClient(true);
        }
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            // 设置请求的编码格式
            builder.setCharset(Consts.UTF_8);
            builder.setContentType(ContentType.MULTIPART_FORM_DATA);
            // 添加文件,也可以添加字节流
            //	builder.addBinaryBody("file", file);
            //或者使用字节流也行,根据具体需要使用
            builder.addBinaryBody("file", Files.readAllBytes(file.toPath()), ContentType.APPLICATION_OCTET_STREAM, file.getName());
            // 或者builder.addPart("file",new FileBody(file));
            // 添加参数
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addTextBody(key, param.get(key));
                }
            }
            HttpEntity reqEntity = builder.build();
            HttpPost httppost = new HttpPost(url);
            // 写入请求BODY参数
            httppost.setEntity(reqEntity);
            // 设置超时时间
            httppost.setConfig(getConfig());
            setHeaderParams(httppost,header);
            response = httpClient.execute(httppost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 设置请求头参数
     * @param http HTTPPOST请求
     * @param headerParams 请求头参数
     */
    public static void setHeaderParams(HttpRequestBase http, Map<String, String> headerParams) {
        String []headers = headerParams.keySet().toArray(new String[0]);
        for (String s : headers) {
            http.addHeader(s, headerParams.get(s));
        }
    }


    /**
     *  辅助方法:这个用来设置超时的;
      */
    public static RequestConfig getConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(60000).setSocketTimeout(120000)
                .setConnectTimeout(60000).build();
    }

    /**
     * 设置SSL请求处理
     */
    public static CloseableHttpClient sslClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
