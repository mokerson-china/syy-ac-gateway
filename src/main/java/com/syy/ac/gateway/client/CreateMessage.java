package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.IotAgent;
import com.syy.ac.gateway.model.message.*;
import com.syy.ac.gateway.util.MqttFileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class CreateMessage {
    private static final String DEVICEINFO_PROPERTIES = "deviceinfo.properties";
    private static final String CONTAINER_PROPERTIES = "containerstatus.properties";
    private DevicePubData devicesData;
    private DeviceServices services;
    private DeviceServiceData serviceData;
    private DeviceStateReplay deviceState;

    public String getVirtualizationGetRep(String method) {
        this.newObject();
        deviceState =  this.setDeviceState(method);
        Containers container = new Containers(MqttFileUtils.readAgentProperty(CONTAINER_PROPERTIES));
        deviceState.setParams(container);
        return this.getDevicesPubData(devicesData);
    }

    public String getPubLoginSetReplyMessage(String method) {
        this.newObject();
        DeviceRegisterReply registerReply = new DeviceRegisterReply();
        registerReply.setMessageId(UUID.randomUUID().toString());
        registerReply.setDeviceId(IotAgent.config.getClientId());
        registerReply.setMethod(method);
        registerReply.setEventTime(new Date());

        return this.getDevicesPubData(registerReply);
    }

    public String getLoginGetReplyMessage() {
        this.newObject();
        deviceState = this.setDeviceState("DeviceState");
        Properties proper = MqttFileUtils.readAgentProperty(DEVICEINFO_PROPERTIES);

        DeviceStateInfo params = new DeviceStateInfo();
        params.setName(IotAgent.config.getDeviceName());
        params.setEsn(IotAgent.config.getDeviceEsn());
        params.setModel(IotAgent.config.getDeviceType());

        params.setMacAddress(proper.getProperty("device.info.macAddress"));
        params.setVendor(proper.getProperty("device.info.vendor"));
        params.setState(proper.getProperty("device.info.state"));
        params.setVersion(proper.getProperty("device.info.version"));
        params.setPatchVersion(proper.getProperty("device.info.patchVersion"));
        params.setKernelVersion(proper.getProperty("device.info.kernelVersion"));
        params.setHardwareVersion(proper.getProperty("device.info.hardwareVersion"));
        params.setClock(new Clock(proper));

        params.setResource(new DeviceResource(proper));
        params.setPerformance(new Performance(proper));
        deviceState.setParams(params);
        return this.getDevicesPubData(deviceState);
    }

    private DeviceStateReplay setDeviceState(String method) {
        deviceState = new DeviceStateReplay();
        deviceState.setParams(UUID.randomUUID().toString());
        deviceState.setParams(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
        deviceState.setParams(method);
        deviceState.setParams(IotAgent.config.getClientId());
        deviceState.setCode(200);
        return deviceState;
    }

    private void newObject() {
        // 第一层的devices
        devicesData = new DevicePubData();
        // 第二层services为第一层devices内的，明确设备的客户端ID和服务列表
        services = new DeviceServices();
        // 第三层serviceData为第二层services内的，明确服务信息和数据
        serviceData = new DeviceServiceData();
    }

    private String getDevicesPubData(Object o) {
        services.setDeviceId(IotAgent.config.getClientId());
        serviceData.setServiceId("ServiceName");
        serviceData.setEventTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));

        serviceData.setData(o);
        services.setServices(serviceData);
        devicesData.setDevices(services);
        return JSONObject.toJSONString(devicesData);
    }
}
