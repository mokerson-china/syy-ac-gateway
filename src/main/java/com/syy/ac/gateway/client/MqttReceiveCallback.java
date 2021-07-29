package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.IotAgent;
import com.syy.ac.gateway.model.AgileControllerFileConfig;
import com.syy.ac.gateway.model.message.RegisterResultMessage;
import com.syy.ac.gateway.util.HttpsFileUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * MQTT协议订阅Topic接受到消息处理
 *
 * @author TanGuozheng
 */
public class MqttReceiveCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttReceiveCallback.class);
    private CreateMessage createMsg;

    public MqttReceiveCallback() {
        createMsg = new CreateMessage();
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
        logger.info("————收到Topic：{}\n消息内容为：————\n{}", topic, message);
        JSONObject messages = JSONObject.parseObject(message.toString());
        String method = messages.getString("method");
        String messageId = messages.getString("messageId");

        // Topic: /{Version}/{DeviceId}/login/get处理
        // 设备注册回复内容，返回设备消息
        if ("DeviceState".equals(method)) {
            // 返回设备的状态数据
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getLoginGetReplyMessage(messageId, method));
        } else if ("DeviceRegister".equals(method)) {
            JSONObject params = messages.getJSONObject("params");
            if ("true".equals(params.getString("result"))) {
                // 设备注册结果回复
                MyMqttClient.publishMessage(IotAgent.config.getPubLoginSetReply(), createMsg.getMethodDeviceInfo(messageId, method));

                // 创建线程，持续保持设备上线
                this.createTimerKeepAlive(IotAgent.config.getSubLogKeepaliveEvent());
            } else {
                logger.info("注册失败，失败原因为：{}", RegisterResultMessage.valueOf(params.getString("failReason")).getDescription());
            }
        } else if (method.equals("ContainerStatus")) {
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getVirtualizationGetRep(messageId, method));
        } else if ("ContainerDownload".equals(method)) {
            logger.info("------------------------------------------------收到下发文件的内容：{}", message);
            JSONArray files = messages.getJSONObject("params").getJSONArray("files");

            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));
            for (int i = 0, j = files.size(); i < j; i++) {
                JSONObject file = files.getJSONObject(i);
                // 初始化文件下载对象
                AgileControllerFileConfig fileConfig = new AgileControllerFileConfig(file);
                String url = fileConfig.getTransferMode() + "://" + fileConfig.getFileServerAddress() + ":" + fileConfig.getFileServerPort() + fileConfig.getFileDirectory() + fileConfig.getName();
                try {
                    logger.info("开始执行下载，下载地址：{}",url);
                    HttpsFileUtil.download(url, null, IotAgent.config.getDownloadPath() + fileConfig.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("下载执行完成，下载地址为：" );
            }
        }

/*
            if(1){
                // 接收到下载文件请求，立即下载文件

                String fileFolder = "84f1981fadcd4a3ca97bd6d472d020c9";
                String fileName = "广东北向应用接入情况统计.txt";
                String url = "https://"+IotAgent.config.getFileHost()+":"+IotAgent.config.getFilePort()+IotAgent.config.getDownloadURL()+"?fileFolder=%s&fileName=%s";
                url = String.format(url, fileFolder, fileName);
                logger.info("请求地址为{}", url);

                Map<String, String> header = new HashMap<>(4);
                header.put("X-HW-ID", IotAgent.config.getX_HW_ID());
                header.put("X-HW-APPKEY", IotAgent.config.getX_HW_APPKEY());
                logger.info("请求header参数：{}", header);
                HttpsFileUtil.download(url,header,IotAgent.config.getDownloadPath()+fileName);
                logger.info("请求成功");
            }else if(topic.equals("...")){
                logger.info("Client 接收消息主题: {}" , topic);
                logger.info("Client 接收消息Qos: {}" , message.getQos());
                logger.info("Client 接收消息内容: {}" , new String(message.getPayload()));
            }*/
    }


    /**
     * 创建线程，持续维持网关在AC上的心跳
     *
     * @param topic  心跳推送的Topic
     */
    private void createTimerKeepAlive(String topic) {
        final long[] countKeep = {0};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                countKeep[0]++;
                MyMqttClient.publishMessage(topic, createMsg.getKeepAlive("KeepAlive"));
                logger.info("成功发起第 {} 次心跳维持。。。。。。", countKeep[0]);
            }
        }, 5000, 60000);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}