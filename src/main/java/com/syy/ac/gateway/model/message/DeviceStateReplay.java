/**
 * Copyright 2021 bejson.com
 */
package com.syy.ac.gateway.model.message;

import com.syy.ac.gateway.model.AgentConfig;

import java.util.Date;
import java.util.UUID;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceStateReplay {

    private AgentConfig config;
    private String messageId;
    private Date timestamp;
    private String deviceId;
    private String method;
    private DeviceStateInfo info;
    private int code;

    public DeviceStateReplay(AgentConfig config) {
        this.config = config;
        messageId = UUID.randomUUID().toString();
        timestamp = new Date();
        method = "DeviceState";

        info = new DeviceStateInfo();
        info.setName(config.getDeviceName());
        info.setEsn(config.getDeviceEsn());

    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public DeviceStateInfo getInfo() {
        return info;
    }

    public void setInfo(DeviceStateInfo info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}