/**
 * Copyright 2021 bejson.com
 */
package com.syy.ac.gateway.model.message;

import com.syy.ac.gateway.model.AgentConfig;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceStateReplay {
    private String messageId;
    private Date timestamp;
    private String deviceId;
    private String method;
    private DeviceStateInfo info;
    private int code;

    public DeviceStateReplay(Properties proper, AgentConfig config) {
        messageId = UUID.randomUUID().toString();
        timestamp = new Date();
        method = "DeviceState";

        info = new DeviceStateInfo();
        info.setName(config.getDeviceName());
        info.setEsn(config.getDeviceEsn());
        info.setMacAddress(proper.getProperty("device.info.macAddress"));
        info.setVendor(proper.getProperty("device.info.vendor"));
        info.setState(proper.getProperty("device.info.state"));
        info.setVersion(proper.getProperty("device.info.version"));
        info.setPatchVersion("device.info.patchVersion");
        info.setKernelVersion("device.info.kernelVersion");
        info.setHardwareVersion("device.info.hardwareVersion");
        info.setClock(new Clock(proper));

        info.setResource(new DeviceResource(proper));
        info.setPerformance(new Performance(proper));
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