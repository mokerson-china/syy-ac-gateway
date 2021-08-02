package com.syy.ac.gateway.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author TanGuozheng
 */
public class Containers {

    private int index;
    private String hyperv;
    private String name;
    private String uuid;
    private String state;
    private String uptime;
    private String version;
    private String architecture;
    private String osType;
    private String osVersion;
    private String ip;
    private String imageName;
    private int cpuCount;
    private String cpuUsage;
    private int memoryTotal;
    private int memoryUsed;
    private int storageTotal;
    private int storageUsed;
    private List<ContainersVolumes> volumes = new ArrayList<>();
    private List<String> devices;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<ContainersVolumes> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<ContainersVolumes> volumes) {
        this.volumes = volumes;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public Containers(Properties proper) {
        this.index = Integer.parseInt(proper.getProperty("device.info.container.index"));
        this.hyperv = proper.getProperty("device.info.container.hyperv");
        this.name = proper.getProperty("device.info.container.name");
        this.uuid = proper.getProperty("device.info.container.uuid");
        this.state = proper.getProperty("device.info.container.state");
        this.uptime = proper.getProperty("device.info.container.uptime");
        this.version = proper.getProperty("device.info.container.version");
        this.architecture = proper.getProperty("device.info.container.architecture");
        this.osType = proper.getProperty("device.info.container.osType");
        this.osVersion = proper.getProperty("device.info.container.osVersion");
        this.ip = proper.getProperty("device.info.container.ip");
        this.cpuCount = Integer.parseInt(proper.getProperty("device.info.container.cpuCount"));
        this.cpuUsage = proper.getProperty("device.info.container.cpuUsage");
        this.memoryTotal = Integer.parseInt(proper.getProperty("device.info.container.memoryTotal"));
        this.memoryUsed = Integer.parseInt(proper.getProperty("device.info.container.memoryUsed"));
        this.storageTotal = Integer.parseInt(proper.getProperty("device.info.container.storageTotal"));
        this.storageUsed = Integer.parseInt(proper.getProperty("device.info.container.storageUsed"));
    }

    public Containers() {}

    public Containers(JSONObject containers) {
        JSONObject params = containers.getJSONObject("params");
        this.uuid = params.getString("containerUuid");
        this.imageName = params.getString("imageName");
        this.name = params.getString("containerName");
        this.index = params.getInteger("containerIndex");
        this.hyperv = params.getString("containerHyperv");
        this.cpuCount = params.getShort("cpuCount");
        this.cpuUsage = params.getString("cpuMask");
        this.memoryTotal = params.getShort("memorySize");
        JSONArray volumes = params.getJSONArray("volumes");
        if (null != volumes && volumes.size() != 0) {
            ContainersVolumes volume = null;
            for (int i = 0, size = volumes.size(); i < size; i++) {
                volume = new ContainersVolumes(volumes.getJSONObject(i));
                this.volumes.add(volume);
            }
        }
        devices = params.getJSONArray("devices").toJavaList(String.class);

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHyperv() {
        return hyperv;
    }

    public void setHyperv(String hyperv) {
        this.hyperv = hyperv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(int memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public int getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(int storageTotal) {
        this.storageTotal = storageTotal;
    }

    public int getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(int storageUsed) {
        this.storageUsed = storageUsed;
    }

}