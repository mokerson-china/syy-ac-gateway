package com.syy.ac.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.client.MqttReceiveCallback;
import com.syy.ac.gateway.client.MyMqttClient;
import com.syy.ac.gateway.model.AgentConfig;
import com.syy.ac.gateway.model.message.DeviceKeepalive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class DeviceKeepaliveRun implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DeviceKeepaliveRun.class);
    private DeviceKeepalive keepalive;
    private String pubTopic;
    private long countKeep = 0;

    public DeviceKeepaliveRun(AgentConfig config) {
        pubTopic = config.getPubKeepaliveEventReply();
        keepalive= new DeviceKeepalive();
        keepalive.setType("KeepAlive");
        keepalive.setDeviceId(config.getClientId());
    }

    @Override
    public void run() {
        countKeep++;
        keepalive.setEventTime(new Date());
        keepalive.setMessageId(UUID.randomUUID().toString());
        MyMqttClient.publishMessage(pubTopic, JSONObject.toJSONString(keepalive));
        logger.info("成功发起第{}次心跳维持。。。。。。",countKeep);
    }
}
