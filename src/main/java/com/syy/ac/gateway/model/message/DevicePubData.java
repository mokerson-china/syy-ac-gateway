package com.syy.ac.gateway.model.message;

import java.util.ArrayList;
import java.util.List;

public class DevicePubData {
    private List<DeviceServices> devices;

    public DevicePubData(){
        devices = new ArrayList<>(2);
    }

    public List<DeviceServices> getDevices() {
        return devices;
    }

    public void setDevices(DeviceServices devices) {
        this.devices.add(devices);
    }
}
