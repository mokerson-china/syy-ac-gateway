package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.model.AgentConfig;
import com.syy.ac.gateway.util.HttpsFileUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MqttReceiveCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttReceiveCallback.class);

    private static AgentConfig config = null;

    public void init(AgentConfig config){
        MqttReceiveCallback.config = config;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    /**
     * 订阅Topic接收消息处理
     * @param topic Topic名称
     * @param message   消息内容
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        if(config.getVirtualizationSet().equals(topic)){
            JSONObject subMessage = JSON.parseObject(message.toString());
            String method = subMessage.getString("method");
            JSONObject params = subMessage.getJSONObject("params");
            switch (method){
                // 文件下载处理
                case "ContainerDownload":
                    JSONArray files = params.getJSONArray("files");

                    for(int i =0,j=files.size();i<j;i++){
                        JSONObject file = files.getJSONObject(i);


                    }
                default:
                    logger.info("消息内容不规范，请检查后再发送：{}",subMessage);
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