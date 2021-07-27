
package com.syy.ac.gateway.model.message;

import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.model.AgentConfig;

import java.text.SimpleDateFormat;
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
    private String timestamp;
    private String deviceId;
    private String method;
    private DeviceStateInfo params;
    private int code;

    public DeviceStateReplay(Properties proper, AgentConfig config, JSONObject paramsObject) {
        this.messageId = paramsObject.getString("messageId");
        this.timestamp =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
        this.method = "DeviceState";
        this.deviceId=config.getClientId();

        params = new DeviceStateInfo();
        params.setName(config.getDeviceName());
        params.setEsn(config.getDeviceEsn());
        params.setModel(config.getDeviceType());

        params.setMacAddress(proper.getProperty("device.info.macAddress"));
        params.setVendor(proper.getProperty("device.info.vendor"));
        params.setState(proper.getProperty("device.info.state"));
        params.setVersion(proper.getProperty("device.info.version"));
        params.setPatchVersion(proper.getProperty("device.info.patchVersion"));
        params.setKernelVersion(proper.getProperty("device.info.kernelVersion"));
        params.setHardwareVersion(proper.getProperty("device.info.hardwareVersion"));
        params.setClock(new Clock(proper));

        params.setResource(new DeviceResource(proper));
        params.setPerformance(new Performance(proper));

        this.code = 200;

    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

    public DeviceStateInfo getParams() {
        return params;
    }

    public void setParams(DeviceStateInfo params) {
        this.params = params;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}