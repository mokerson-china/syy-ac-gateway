package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.model.AgentConfig;
import com.syy.ac.gateway.model.AgileControllerFileConfig;
import com.syy.ac.gateway.model.message.DeviceKeepalive;
import com.syy.ac.gateway.model.message.DeviceRegisterReply;
import com.syy.ac.gateway.model.message.DeviceStateReplay;
import com.syy.ac.gateway.model.message.RegisterResultMessage;
import com.syy.ac.gateway.util.MqttFileUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * MQTT协议订阅Topic接受到消息处理
 *
 * @author TanGuozheng
 */
public class MqttReceiveCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttReceiveCallback.class);
    private static final String DEVICEINFO_PROPERTIES = "deviceinfo.properties";

    private static AgentConfig config = null;
    /**
     * 创建线程
     */
    private static ExecutorService exec = java.util.concurrent.Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r);
        t.setName("worker-thread-" + UUID.randomUUID().toString());
        return t;
    });

    public void init(AgentConfig config) {
        MqttReceiveCallback.config = config;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    /**
     * 订阅Topic接收消息处理
     *
     * @param topic   Topic名称
     * @param message 消息内容
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info("收到Topic{}的消息，消息内容为\n{}", topic, message);

        if(topic.equals(config.getSubLogKeepaliveEvent())){
            JSONObject messages = JSONObject.parseObject(message.toString());
            DeviceKeepalive keepalive = new DeviceKeepalive();
            keepalive.setMessageId(messages.getString("messageId"));
            keepalive.setType("KeepAlive");
            keepalive.setEventTime(new Date());
            keepalive.setDeviceId(config.getClientId());


            MyMqttClient.publishMessage(config.getPubKeepaliveEventReply(), JSONObject.toJSONString(keepalive));
            // 设备上线成功，维持设备上线状态，后台线程定时刷新
//            ExecutorService executor = Executors.newFixedThreadPool(2);
//            executor.submit(new DeviceKeepaliveRun(config));
        } else if (config.getSubLoginGet().equals(topic)) {
            // 设备注册回复内容，返回设备消息
            JSONObject messages = JSONObject.parseObject(message.toString());
            String method = messages.getString("method");
            if ("DeviceState".equals(method)) {
                // 生成设备信息，返回给AC平台
                DeviceStateReplay device = new DeviceStateReplay(MqttFileUtils.readAgentProperty(DEVICEINFO_PROPERTIES),config);
                MyMqttClient.publishMessage(config.getPubLoginGetReply(),JSONObject.toJSONString(device));
            } else if("DeviceRegister".equals(method)){
                JSONObject params = messages.getJSONObject("params");
                if("ture".equals(params.getString("result"))){
                    // 设备注册结果回复
                    // 配置注册结果回复
                    DeviceRegisterReply registerReply = new DeviceRegisterReply();
                    registerReply.setMessageId(messages.getString("messageId"));
                    registerReply.setDeviceId(config.getClientId());
                    registerReply.setMethod("DeviceRegister");
                    registerReply.setEventTime(new Date());
                }else{
                    logger.info("注册失败，失败原因为：{}", RegisterResultMessage.valueOf(params.getString("failReason")).getDescription());
                }
            }
        }else if (config.getVirtualizationSet().equals(topic)) {
            JSONObject subMessage = JSON.parseObject(message.toString());
            String method = subMessage.getString("method");
            JSONObject params = subMessage.getJSONObject("params");
            switch (method) {
                // 接收到文件下载消息
                case "ContainerDownload":
                    JSONArray files = params.getJSONArray("files");

                    for (int i = 0, j = files.size(); i < j; i++) {
                        JSONObject file = files.getJSONObject(i);
                        // 初始化文件下载对象
                        AgileControllerFileConfig fileConfig = new AgileControllerFileConfig(file);
                        String url = fileConfig.getTransferMode() + "://" + fileConfig.getFileServerAddress() + ":" + fileConfig.getFileServerPort() + fileConfig.getFileDirectory() + "?fileFolder=%s&fileName=%s";
                    }
                default:
                    logger.info("消息内容不规范，请检查后再发送：{}", subMessage);
            }
/*
            if(1){
                logger.info("Client 接收消息主题: {}" , topic);
                logger.info("Client 接收消息Qos: {}" , message.getQos());
                logger.info("Client 接收消息内容: {}" , new String(message.getPayload()))
                // 接收到下载文件请求，立即下载文件

                String fileFolder = "84f1981fadcd4a3ca97bd6d472d020c9";
                String fileName = "广东北向应用接入情况统计.txt";
                String url = "https://"+config.getFileHost()+":"+config.getFilePort()+config.getDownloadURL()+"?fileFolder=%s&fileName=%s";
                url = String.format(url, fileFolder, fileName);
                logger.info("请求地址为{}", url);

                Map<String, String> header = new HashMap<>(4);
                header.put("X-HW-ID", config.getX_HW_ID());
                header.put("X-HW-APPKEY", config.getX_HW_APPKEY());
                logger.info("请求header参数：{}", header);
                HttpsFileUtil.download(url,header,config.getDownloadPath()+fileName);
                logger.info("请求成功");
            }else if(topic.equals("...")){
                logger.info("Client 接收消息主题: {}" , topic);
                logger.info("Client 接收消息Qos: {}" , message.getQos());
                logger.info("Client 接收消息内容: {}" , new String(message.getPayload()));
            }*/
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}