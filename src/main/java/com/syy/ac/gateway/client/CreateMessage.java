package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.IotAgent;
import com.syy.ac.gateway.message.*;
import com.syy.ac.gateway.util.MqttFileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateMessage {
    private static final String DEVICEINFO_PROPERTIES = "deviceinfo.properties";
    private static final String CONTAINER_PROPERTIES = "containerstatus.properties";
    private DevicePubData devicesData;
    private DeviceServices services;
    private DeviceServiceData serviceData;

    /**
     * 生成容器状态数据
     * @param method    容器方法
     * @return  JSON格式
     */
    public String getVirtualizationGetRep(String messageId,String method) {
        this.newObject();
        DeviceStateReplay deviceState;
        deviceState =  this.setDeviceState(messageId,method);
        List<Containers > container = new ArrayList<>() ;
        container.add(new Containers(MqttFileUtils.readAgentProperty(CONTAINER_PROPERTIES)));
        JSONObject containers = new JSONObject();
        containers.put("containers",container);
        deviceState.setParams(containers);
        return this.getDevicesPubData(deviceState);
    }

    /**
     * 生成注册返回的设备状态数据
     * @param method    注册方法
     * @return  JSON格式
     */
    public String getMethodDeviceInfo(String messageId, String method) {
        this.newObject();
        DeviceRegisterReply registerReply = new DeviceRegisterReply();
        registerReply.setMessageId(messageId);
        registerReply.setDeviceId(IotAgent.config.getClientId());
        registerReply.setMethod(method);
        registerReply.setEventTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));

        return this.getDevicesPubData(registerReply);
    }

    /**
     * 生成注册后响应的设备状态内容
     * @return  JSON格式消息
     */
    public String getLoginGetReplyMessage(String messageId,String method) {
        this.newObject();
        DeviceStateReplay deviceState = this.setDeviceState(messageId,method);
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

    /**
     * 生成设备的状态数据
     * @param method   设备状态方法
     * @return  JSON格式消息
     */
    public DeviceStateReplay setDeviceState(String messageId,String method) {
        DeviceStateReplay deviceState = new DeviceStateReplay();
        deviceState.setMessageId(messageId);
        deviceState.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
        deviceState.setMethod(method);
        deviceState.setDeviceId(IotAgent.config.getClientId());
        deviceState.setCode(200);
        return deviceState;
    }

    /**
     * 为每次发送不同类型消息的时候创建新的对象
     */
    private void newObject() {
        // 第一层的devices
        devicesData = new DevicePubData();
        // 第二层services为第一层devices内的，明确设备的客户端ID和服务列表
        services = new DeviceServices();
        // 第三层serviceData为第二层services内的，明确服务信息和数据
        serviceData = new DeviceServiceData();
    }

    /**
     * 返回标准的ROMA上报数据格式
     * @param o 任何对象
     * @return  JSON格式
     */
    private String getDevicesPubData(Object o) {
        services.setDeviceId(IotAgent.config.getClientId());
        serviceData.setServiceId(UUID.randomUUID().toString());
        serviceData.setEventTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));

        serviceData.setData(o);
        services.setServices(serviceData);
        devicesData.setDevices(services);
        return JSONObject.toJSONString(devicesData);
    }

    /**
     * 第三方网关心跳维持消息获取
     * @param method    心跳维持方法
     * @return  心跳维持消息JSON格式
     */
    public String getKeepAlive(String method) {
        this.newObject();
        DeviceKeepalive keepalive = new DeviceKeepalive();
        keepalive.setType(method);
        keepalive.setDeviceId(IotAgent.config.getClientId());
        keepalive.setEventTime(new Date());
        keepalive.setMessageId(UUID.randomUUID().toString());
        return this.getDevicesPubData(keepalive);
    }

    public String getDownloadStatus(JSONArray fileNames,String method,String messageId) {
        this.newObject();
        DeviceStateReplay params = setDeviceState(method,messageId);
        List<DownloadFileStatus> files = new ArrayList<>();
        for(int i = 0,j=fileNames.size();i<j;i++){
            String name = fileNames.getJSONObject(i).getString("name");
            File file = new File(IotAgent.config.getDownloadPath()+name);
            DownloadFileStatus status = new DownloadFileStatus();
            status.setType("lxc-container-ova-pkg");
            // mkdirs()文件夹不存在那么就会创建，存在则返回false
            if(file.getParentFile().mkdirs()){
                // 文件不存在
                status.setStatus("Error");
                status.setPercentage(0);
                status.setErrorCode(1000);
            }else{
                // 文件已存在
                status.setStatus("Success");
                status.setPercentage(100);
                status.setErrorCode(0);
            }
            status.setName(name);
            files.add(status);
        }
        params.setParams(files);
        return this.getDevicesPubData(params);
    }

    public String getContainerStorageMedia(String method, String messageId) {
        this.newObject();
        DeviceStateReplay params = setDeviceState(method,messageId);
        ContainerStorageMedia storageMedia = new ContainerStorageMedia();
        params.setParams(storageMedia);
        return this.getDevicesPubData(params);
    }
}
