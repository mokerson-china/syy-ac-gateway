package com.syy.ac.gateway;

import com.syy.ac.gateway.model.AgentConfig;
import com.syy.ac.gateway.util.HttpClientUtil;
import com.syy.ac.gateway.util.MqttFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class IotAgent {
    public static final String PROPERTY_FILE_PATH = "iotagent.properties";
    private static final Logger logger = LoggerFactory.getLogger(IotAgent.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
/*        AgentConfig config = readAgentProperty();
        initialBackupPath(config);
        BasicConfigurator.configure();
        MyMqttClient mqttClient = new MyMqttClient();

        mqttClient.init(config);

        JSONObject message = new JSONObject();
        message.put("key","value");
        message.put("reportTime", dateFormat.format(new Date()));
        mqttClient.publishMessage("/v1/devices/Gateway_Device/datas","{\n" +
                "    \"devices\": [\n" +
                "        {\n" +
                "            \"deviceId\": \"D527212570kp3O\",\n" +
                "            \"services\": [\n" +
                "                {\n" +
                "                    \"data\": {\n" +
                "                        \"key\": \"value\"\n" +
                "                    },\n" +
                "                    \"eventTime\": \"20191023T173625Z\",\n" +
                "                    \"serviceId\": \"serviceName\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        mqttClient.subTopic("/v1/devices/"+config.getGatewayId()+"/command");*/

        /*//文件上传
        String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/upload";
        Map<String,String> map = new HashMap<>(4);
        File file = new File("E:\\常用文档\\工作常用文档\\广东北向应用接入情况统计.txt");
        map.put("fileFolder", String.valueOf(UUID.randomUUID()).replace("-",""));
        map.put("uploadType","1");*/

   /*     String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/download?fileFolder=%&fileName=%";
        url = String.format(url,"84f1981fadcd4a3ca97bd6d472d020c9","广东北向应用接入情况统计.txt");
        logger.info("请求地址为{}",url);

        Map<String,String> header = new HashMap<>(4);
        header.put("X-HW-ID", "iotcenter.key");
        header.put("X-HW-APPKEY", "PU1/pOh1PpSxqAfy1a5Ulg==");
        logger.info("请求header参数：{}",header);*/

        String url = "https://iot-data-cloud.oss-cn-guangzhou.aliyuncs.com/video/V1/SystemToIOTOperationVideo.zip\n";
        logger.info("请求成功，响应结果");
    }

    /**
     * 设置备份目录
     * @param config    配置信息
     */
    private static void initialBackupPath(AgentConfig config) {
        String backupFolder = config.getBackupFolder();
        if (backupFolder == null) {
            throw new RuntimeException("Can not locate backup folder");
        } else {
            File backupFolderDir = new File(backupFolder);
            if (!backupFolderDir.exists() || !backupFolderDir.isDirectory()) {
                if (backupFolderDir.exists() && !backupFolderDir.isDirectory()) {
                    logger.error("Backup folder is not a directory, agent can not use");
                    throw new RuntimeException("Failed to create backup folder");
                } else {
                    try {
                        MqttFileUtils.createFolders(backupFolder);
                    } catch (IOException exception) {
                        logger.error("Backup folder create failed.", exception);
                        throw new RuntimeException("Failed to create backup folder", exception);
                    }
                }
            }
        }
    }

    /**
     * 读取配置文件
     * @return 配置内容
     */
    private static AgentConfig readAgentProperty() {
        InputStream propertyStream = IotAgent.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_PATH);

        AgentConfig agentConfig;
        try {
            Properties properties = new Properties();
            properties.load(propertyStream);
            agentConfig = new AgentConfig(properties);
        } catch (IOException var11) {
            logger.error("Failed to load property", var11);
            throw new RuntimeException("Property load failed");
        } finally {
            if (propertyStream != null) {
                try {
                    propertyStream.close();
                } catch (IOException var10) {
                    logger.error("Failed to close property stream", var10);
                }
            }

        }
        return agentConfig;
    }


}
