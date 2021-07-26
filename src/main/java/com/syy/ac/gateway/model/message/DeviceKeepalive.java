package com.syy.ac.gateway.model.message;

import java.util.Date;

public class DeviceKeepalive {

    private String messageId;
    private String deviceId;
    private String type;
    private Date eventTime;
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

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    public Date getEventTime() {
        return eventTime;
    }

}