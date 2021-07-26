/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;
import java.util.Date;


/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class LoginEvent {

    private String messageId;
    private Date timestamp;
    private String deviceId;
    private String type;
    private Date eventTime;
    public void setMessageId(String messageId) {
         this.messageId = messageId;
     }
     public String getMessageId() {
         return messageId;
     }

    public void setTimestamp(Date timestamp) {
         this.timestamp = timestamp;
     }
     public Date getTimestamp() {
         return timestamp;
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