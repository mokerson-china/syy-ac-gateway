package com.syy.ac.gateway.message;

public class DeviceRegisterReply {

    private String messageId;
    private String deviceId;
    private String method;
    private String eventTime;
    private int code = 200;

    public DeviceRegisterReply() {

    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getMessageId() {
        return messageId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getMethod() {
        return method;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public String getEventTime() {
        return eventTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}