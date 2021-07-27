package com.syy.ac.gateway.model.message;

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
    private int cpuCount;
    private String cpuUsage;
    private int memoryTotal;
    private int memoryUsed;
    private int storageTotal;
    private int storageUsed;

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

    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public void setHyperv(String hyperv) {
        this.hyperv = hyperv;
    }
    public String getHyperv() {
        return hyperv;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
    public String getUptime() {
        return uptime;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }
    public String getArchitecture() {
        return architecture;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
    public String getOsType() {
        return osType;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    public String getOsVersion() {
        return osVersion;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }
    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }
    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setMemoryTotal(int memoryTotal) {
        this.memoryTotal = memoryTotal;
    }
    public int getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }
    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setStorageTotal(int storageTotal) {
        this.storageTotal = storageTotal;
    }
    public int getStorageTotal() {
        return storageTotal;
    }

    public void setStorageUsed(int storageUsed) {
        this.storageUsed = storageUsed;
    }
    public int getStorageUsed() {
        return storageUsed;
    }

}