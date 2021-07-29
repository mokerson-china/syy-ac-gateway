/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.message;
import com.syy.ac.gateway.message.DevicesDataInfo;

import java.util.List;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceRegisterReplay {

    private List<DevicesDataInfo> devices;
    public void setDevices(List<DevicesDataInfo> devices) {
         this.devices = devices;
     }
     public List<DevicesDataInfo> getDevices() {
         return devices;
     }

}