package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.IotAgent;
import com.syy.ac.gateway.model.AgileControllerFileConfig;
import com.syy.ac.gateway.model.message.RegisterResultMessage;
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
    private CreateMessage createMsg ;

    public MqttReceiveCallback(){
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
        if(topic.equals(IotAgent.config.getSubLogKeepaliveEvent())){
//            JSONObject messages = JSONObject.parseObject(message.toString());
//            DeviceKeepalive keepalive = new DeviceKeepalive();
//            keepalive.setMessageId(messages.getString("messageId"));
//            keepalive.setType("KeepAlive");
//            keepalive.setEventTime(new Date());
//            keepalive.setDeviceId(IotAgent.config.getClientId());
//
//            MyMqttClient.publishMessage(IotAgent.config.getPubKeepaliveEventReply(), JSONObject.toJSONString(keepalive));
            // 设备上线成功，维持设备上线状态，后台线程定时刷新
            logger.info("收到维持心跳数据：{}",message.toString());
        } else if (topic.equals(IotAgent.config.getSubLoginGet())) {
            // Topic: /{Version}/{DeviceId}/login/get处理
            // 设备注册回复内容，返回设备消息
            String method = messages.getString("method");
            if ("DeviceState".equals(method)) {
                // 返回设备的状态数据
                MyMqttClient.publishMessage(IotAgent.config.getPubLoginGetReply(),createMsg.getLoginGetReplyMessage());
            } else if("DeviceRegister".equals(method)){

            }
        }else if(topic.equals(IotAgent.config.getSubLoginSet())){
//          设备注册结果回复
            String method = messages.getString("method");
            JSONObject params = messages.getJSONObject("params");
            if("ture".equals(params.getString("result"))){
                // 设备注册结果回复
                MyMqttClient.publishMessage(IotAgent.config.getPubLoginSetReply(),createMsg.getPubLoginSetReplyMessage(method));

                // 创建线程，持续保持设备上线
                this.createTimerKeepAlive(IotAgent.config.getPubKeepaliveEventReply(),method);
            }else{
                logger.info("注册失败，失败原因为：{}", RegisterResultMessage.valueOf(params.getString("failReason")).getDescription());
            }
        }else if(topic.equals(IotAgent.config.getVirtualizationGet())){
            // 获取容器状态
            String method = messages.getString("method");
            if(method.equals("ContainerStatus")){
                MyMqttClient.publishMessage(IotAgent.config.getVirtualizationGetRep(),createMsg.getVirtualizationGetRep(method));
            }
        }else if (topic.equals(IotAgent.config.getVirtualizationSet())) {
            String method = messages.getString("method");
            JSONObject params = messages.getJSONObject("params");
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
                    logger.info("消息内容不规范，请检查后再发送：{}", messages);
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
    }

    /**
     * 创建线程，持续维持网关在AC上的心跳
     * @param topic 心跳推送的Topic
     * @param method    心跳方法
     */
    private void createTimerKeepAlive(String topic,String method) {
        final long[] countKeep = {0};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                countKeep[0]++;
                MyMqttClient.publishMessage(topic, createMsg.getKeepAlive(method));
                logger.info("成功发起第 {} 次心跳维持。。。。。。", countKeep[0]);
            }
        }, 200000 , 1000);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}