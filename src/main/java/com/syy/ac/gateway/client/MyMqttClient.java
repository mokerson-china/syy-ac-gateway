package com.syy.ac.gateway.client;

import com.syy.ac.gateway.IotAgent;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQTT客户端连接配置类
 *
 * @author TanGuozheng
 */
public class MyMqttClient {

    private static final Logger log = LoggerFactory.getLogger(MyMqttClient.class);
    public static MqttClient mqttClient = null;
    private static MemoryPersistence memoryPersistence = null;
    private static MqttConnectOptions mqttConnectOptions = null;

    public static void init() {
        // 初始化连接设置对象, true可以安全地使用内存持久性作为客户端断开连接时清除的所有状态, 设置连接超时
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(30);
        mqttConnectOptions.setMaxInflight(1000);
        mqttConnectOptions.setUserName(IotAgent.config.getConnectorUser());
        mqttConnectOptions.setPassword(IotAgent.config.getConnectorPassword().toCharArray());
        // 设置持久化方式
        memoryPersistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient("tcp://" + IotAgent.config.getConnectorHost() + ":" + IotAgent.config.getConnectorPort(), IotAgent.config.getClientId(), memoryPersistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        log.info(String.valueOf(mqttClient.isConnected()));
        // 设置连接和回调
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                // 创建回调函数对象, 客户端添加回调函数
                MqttReceiveCallback mqttReceiveCallback = new MqttReceiveCallback();
                mqttClient.setCallback(mqttReceiveCallback);
                mqttClient.setTimeToWait(2000);
                log.info(mqttConnectOptions.toString());
                // 创建连接
                try {
                    log.info("创建MQTT连接");
                    mqttClient.connect(mqttConnectOptions);
                    // 连接成功后开始订阅MQTT Topic
                    String[] topics = IotAgent.config.getSubTopic();
                    subTopic(topics);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
        MqttReceiveCallback.registerDeviceInfo();
    }

    //	发布消息
    public static void publishMessage(String pubTopic, String message, int qos) {
        if (null != mqttClient && mqttClient.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());

            MqttTopic topic = mqttClient.getTopic(pubTopic);

            if (null != topic) {
                try {
                    log.info("设备开始发布消息，发布主题为：{}", pubTopic);
                    log.info("发布内容为：{}", message);
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    if (!publish.isComplete()) {
                        log.info("消息发布成功......");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

        } else {
            log.info("消息发布异常，请重试");
            reConnect();
        }
    }

    //	发布消息
    public static void publishMessage(String pubTopic, String message) {
        publishMessage(pubTopic, message, 1);
    }

    //	重新连接
    public static void reConnect() {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        e.printStackTrace();
                        // 关闭连接
                        closeConnect();
                        // 重新连接
                        init();
                        // 连接后重新订阅
                    }
                } else {
                    log.info("mqttConnectOptions is null");
                }
            } else {
                log.info("mqttClient is null or connect");
            }
        } else {
            log.error("MQTT程序异常。。。");
        }
    }

    //	订阅主题
    public static void subTopic(String[] topic) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                mqttClient.subscribe(topic);
                int i = 0;
                for (String s : topic) {
                    i++;
                    log.info("MQTT程序成功订阅第{}个主题，主题名称为：{}", i, s);
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.info("客户端ID不存在或者是连接异常");
        }
    }

    //	关闭连接
    public static void closeConnect() {
        //关闭存储方式
        if (null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            }
        } else {
            log.info("memoryPersistence is null");
        }

//		关闭连接
        if (null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("mqttClient is not connect");
            }
        } else {
            log.info("mqttClient is null");
        }
    }

    //	订阅主题
    public void subTopic(String topic) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                log.info("订阅主题成功，主题名为：{}", topic);
                mqttClient.subscribe(topic, 1);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.info("客户端ID不存在或者是连接异常");
        }
    }

    //	清空主题
    public void cleanTopic(String topic) {
        if (null != mqttClient && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.info("mqttClient is error");
        }
    }
}