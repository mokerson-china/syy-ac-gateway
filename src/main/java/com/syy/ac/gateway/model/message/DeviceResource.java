/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;


import java.util.Properties;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceResource {

    private GatewayCpu cpu;

    private GatewayMemory memory;

    public DeviceResource(Properties proper) {
        this.cpu = new GatewayCpu(Integer.parseInt(proper.getProperty("device.info.resource.cpu.total")));
        this.memory = new GatewayMemory(Integer.parseInt(proper.getProperty("device.info.resource.memory.total")));
    }

    public void setCpu(GatewayCpu cpu) {
         this.cpu = cpu;
     }
     public GatewayCpu getCpu() {
         return cpu;
     }

    public void setMemory(GatewayMemory memory) {
         this.memory = memory;
     }
     public GatewayMemory getMemory() {
         return memory;
     }

}