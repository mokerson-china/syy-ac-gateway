package com.syy.ac.gateway.util;

import com.syy.ac.gateway.client.CreateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceKeepaliveRun implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DeviceKeepaliveRun.class);

    private String pubTopic;
    private long countKeep = 0;
    private CreateMessage msg;

    public DeviceKeepaliveRun(String topic) {
        pubTopic = topic;
    }

    @Override
    public void run() {

    }
}
