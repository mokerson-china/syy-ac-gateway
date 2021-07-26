/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class Performance {

    private int cpuUseRate;
    private int memoryUseRate;
    private List<StoragesUseRate> storagesUseRate;

    public Performance(Properties proper) {
        cpuUseRate = Integer.parseInt(proper.getProperty("device.info.performance.cpuUseRate"));
        memoryUseRate = Integer.parseInt(proper.getProperty("device.info.performance.memoryUseRate"));
        String []storages = proper.getProperty("device.info.performance.storagesUseRate").split(";");
        storagesUseRate = new ArrayList<>(storages.length);
        StoragesUseRate temp = new StoragesUseRate();
        for (String s : storages) {
            String[] storage = s.split(",");
            temp.setDevice(storage[0]);
            temp.setTotalSize(Integer.parseInt(storage[1]));
            temp.setUsedSize(Integer.parseInt(storage[2]));
        }

    }

    public void setCpuUseRate(int cpuUseRate) {
         this.cpuUseRate = cpuUseRate;
     }
     public int getCpuUseRate() {
         return cpuUseRate;
     }

    public void setMemoryUseRate(int memoryUseRate) {
         this.memoryUseRate = memoryUseRate;
     }
     public int getMemoryUseRate() {
         return memoryUseRate;
     }

    public void setStoragesUseRate(List<StoragesUseRate> storagesUseRate) {
         this.storagesUseRate = storagesUseRate;
     }
     public List<StoragesUseRate> getStoragesUseRate() {
         return storagesUseRate;
     }

}