package com.syy.ac.gateway.model.message;

import java.util.Date;

public class DeviceRegisterReply {

    private String messageId;
    private String deviceId;
    private String method;
    private Date eventTime;

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

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    public Date getEventTime() {
        return eventTime;
    }

}