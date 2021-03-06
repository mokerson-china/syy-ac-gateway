package com.syy.ac.gateway;

import com.syy.ac.gateway.client.MyMqttClient;
import com.syy.ac.gateway.config.AgentConfig;
import com.syy.ac.gateway.util.MqttFileUtils;
import com.syy.ac.gateway.util.RomaLinkAcInitialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * 第三方网关接入AC启动类
 * @author TanGuozheng
 */
public class IotAgent {
    private static final String IOTAGENT_PROPERTIES = "iotagent.properties";
    private static final Logger logger = LoggerFactory.getLogger(IotAgent.class);
    public static AgentConfig config = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        config = new AgentConfig(MqttFileUtils.readAgentProperty(IOTAGENT_PROPERTIES));
        //RomaLinkAcInitialize acInit = new RomaLinkAcInitialize(config);
        MyMqttClient.init();

        /*//文件上传
        String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/upload";
        Map<String,String> map = new HashMap<>(4);
        File file = new File("E:\\常用文档\\工作常用文档\\广东北向应用接入情况统计.txt");
        map.put("fileFolder", String.valueOf(UUID.randomUUID()).replace("-",""));
        map.put("uploadType","1");*/

   /*   String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/download?fileFolder=%&fileName=%";
        url = String.format(url,"84f1981fadcd4a3ca97bd6d472d020c9","广东北向应用接入情况统计.txt");
        logger.info("请求地址为{}",url);

        Map<String,String> header = new HashMap<>(4);
        header.put("X-HW-ID", "iotcenter.key");
        header.put("X-HW-APPKEY", "PU1/pOh1PpSxqAfy1a5Ulg==");
        logger.info("请求header参数：{}",header);*/

        // String url = "https://iot-data-cloud.oss-cn-guangzhou.aliyuncs.com/video/V1/SystemToIOTOperationVideo.zip";

        // 文件传输
/*        String fileFolder = "84f1981fadcd4a3ca97bd6d472d020c9";
        String fileName = "广东北向应用接入情况统计.txt";
        String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/download?fileFolder=%s&fileName=%s";
        url = String.format(url, fileFolder, fileName);
        logger.info("请求地址为{}", url);

        Map<String, String> header = new HashMap<>(4);
        header.put("X-HW-ID", "iotcenter.key");
        header.put("X-HW-APPKEY", "PU1/pOh1PpSxqAfy1a5Ulg==");
        logger.info("请求header参数：{}", header);
        HttpsFileUtil.download(url,header,"D:\\TEST\\"+fileName);
        logger.info("请求成功");*/

        // 解压文件
    }

}
