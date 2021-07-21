package com.syy.ac.gateway.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttReceiveCallback implements MqttCallback {

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
        System.out.println("Client 接收消息主题: " + topic);
        System.out.println("Client 接收消息Qos: " + message.getQos());
        System.out.println("Client 接收消息内容: " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}