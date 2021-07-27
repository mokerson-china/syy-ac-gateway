/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;
import java.util.List;


/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DevicesDataInfo {

    private String deviceId;
    private List<DeviceServices> services;
    public void setDeviceId(String deviceId) {
         this.deviceId = deviceId;
     }
     public String getDeviceId() {
         return deviceId;
     }

    public void setServices(List<DeviceServices> services) {
         this.services = services;
     }
     public List<DeviceServices> getServices() {
         return services;
     }

}