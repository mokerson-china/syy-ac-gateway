package com.syy.ac.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.client.MyMqttClient;
import com.syy.ac.gateway.model.AgentConfig;
import com.syy.ac.gateway.model.message.DeviceKeepalive;

import java.util.Date;
import java.util.UUID;

public class DeviceKeepaliveRun implements Runnable{
    private DeviceKeepalive keepalive;
    private String pubTopic;

    public DeviceKeepaliveRun(AgentConfig config) {
        pubTopic = config.getPubKeepaliveEventReply();
        keepalive= new DeviceKeepalive();
        keepalive.setType("KeepAlive");
        keepalive.setDeviceId(config.getClientId());
    }

    @Override
    public void run() {
        keepalive.setEventTime(new Date());
        keepalive.setMessageId(UUID.randomUUID().toString());
        MyMqttClient.publishMessage(pubTopic, JSONObject.toJSONString(keepalive));
    }
}
