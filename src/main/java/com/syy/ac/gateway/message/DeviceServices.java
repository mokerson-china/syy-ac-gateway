/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceServices {

    private List<DeviceServiceData> services ;
    private String deviceId;

    public DeviceServices(){
        services = new ArrayList<>(2);
    }

    public void setServices(DeviceServiceData services) {
         this.services.add(services);
     }
     public  List<DeviceServiceData> getServices() {
         return services;
     }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}