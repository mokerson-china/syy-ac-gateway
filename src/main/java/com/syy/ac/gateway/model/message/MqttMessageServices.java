/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class MqttMessageServices {

    private LoginEvent data;
    private String eventTime;
    private String serviceId;
    public void setData(LoginEvent data) {
         this.data = data;
     }
     public LoginEvent getData() {
         return data;
     }

    public void setEventTime(String eventTime) {
         this.eventTime = eventTime;
     }
     public String getEventTime() {
         return eventTime;
     }

    public void setServiceId(String serviceId) {
         this.serviceId = serviceId;
     }
     public String getServiceId() {
         return serviceId;
     }

}