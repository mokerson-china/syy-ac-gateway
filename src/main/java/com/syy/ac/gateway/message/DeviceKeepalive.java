package com.syy.ac.gateway.message;

import java.util.Date;

public class DeviceKeepalive {

    private String messageId;
    private String deviceId;
    private String type;
    private String eventTime;

    public DeviceKeepalive() {

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

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public String getEventTime() {
        return eventTime;
    }

}