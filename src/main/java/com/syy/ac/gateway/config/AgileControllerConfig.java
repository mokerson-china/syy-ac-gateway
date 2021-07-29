package com.syy.ac.gateway.config;

import java.util.Properties;

public class AgileControllerConfig extends FileConfig{

    private String romaAppId;
    private String romaAppKey;
    private String acHost;
    private String acPort;
    /**
     * 第一种鉴权方式
     */
    private String acUserName;
    private String acPassword;
    private String acTokenURL;
    /**
     * 第二种鉴权方式
     */
    private String acUserNameAcIot;
    private String acPasswordAcIot;
    private String acTokenURLAcIot;
    /**
     * 新增的应用
     */
    private String addAppInfoURL;
    private String addDeviceInfoURL;
    /**
     * 新增的设备档案信息
     */
    private String deviceEsn;
    private String deviceName;
    private String deviceType;
    private String deviceDescription;
    private String deviceThirdPartyClientId;
    private String deviceThirdPartyAppId;


    public AgileControllerConfig(Properties props) {
        super(props);
        this.romaAppId = props.getProperty("ac.add.roma.appId");
        this.romaAppKey = props.getProperty("ac.add.roma.appKey");
        this.acHost = props.getProperty("ac.http.host");
        this.acPort = props.getProperty("ac.http.port");
        this.acUserName = props.getProperty("ac.http.userName");
        this.acPassword = props.getProperty("ac.http.password");
        this.acTokenURL = props.getProperty("ac.http.getToken");
        this.addAppInfoURL = props.getProperty("ac.http.add.appInfo");
        this.addDeviceInfoURL = props.getProperty("ac.http.add.deviceInfo");

        this.acUserNameAcIot = props.getProperty("ac.http.userNameAcIot");
        this.acPasswordAcIot = props.getProperty("ac.http.passwordAcIot");
        this.acTokenURLAcIot = props.getProperty("ac.http.getTokenAcIot");

        this.deviceEsn = props.getProperty("ac.add.roma.device.esn");
        this.deviceName = props.getProperty("ac.add.roma.device.name");
        this.deviceType = props.getProperty("ac.add.roma.device.type");
        this.deviceDescription = props.getProperty("ac.add.roma.device.description");
        this.deviceThirdPartyClientId = props.getProperty("ac.add.roma.device.thirdPartyClientId");
        this.deviceThirdPartyAppId = props.getProperty("ac.add.roma.device.thirdPartyAppId");
    }

    public String getAcUserNameAcIot() {
        return acUserNameAcIot;
    }

    public void setAcUserNameAcIot(String acUserNameAcIot) {
        this.acUserNameAcIot = acUserNameAcIot;
    }

    public String getAcPasswordAcIot() {
        return acPasswordAcIot;
    }

    public void setAcPasswordAcIot(String acPasswordAcIot) {
        this.acPasswordAcIot = acPasswordAcIot;
    }

    public String getAcTokenURLAcIot() {
        return acTokenURLAcIot;
    }

    public void setAcTokenURLAcIot(String acTokenURLAcIot) {
        this.acTokenURLAcIot = acTokenURLAcIot;
    }

    public String getDeviceEsn() {
        return deviceEsn;
    }

    public void setDeviceEsn(String deviceEsn) {
        this.deviceEsn = deviceEsn;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceThirdPartyClientId() {
        return deviceThirdPartyClientId;
    }

    public void setDeviceThirdPartyClientId(String deviceThirdPartyClientId) {
        this.deviceThirdPartyClientId = deviceThirdPartyClientId;
    }

    public String getDeviceThirdPartyAppId() {
        return deviceThirdPartyAppId;
    }

    public void setDeviceThirdPartyAppId(String deviceThirdPartyAppId) {
        this.deviceThirdPartyAppId = deviceThirdPartyAppId;
    }

    public String getAcTokenURL() {
        return acTokenURL;
    }

    public void setAcTokenURL(String acTokenURL) {
        this.acTokenURL = acTokenURL;
    }

    public String getRomaAppId() {
        return romaAppId;
    }

    public void setRomaAppId(String romaAppId) {
        this.romaAppId = romaAppId;
    }

    public String getRomaAppKey() {
        return romaAppKey;
    }

    public void setRomaAppKey(String romaAppKey) {
        this.romaAppKey = romaAppKey;
    }

    public String getAcHost() {
        return acHost;
    }

    public void setAcHost(String acHost) {
        this.acHost = acHost;
    }

    public String getAcPort() {
        return acPort;
    }

    public void setAcPort(String acPort) {
        this.acPort = acPort;
    }

    public String getAcUserName() {
        return acUserName;
    }

    public void setAcUserName(String acUserName) {
        this.acUserName = acUserName;
    }

    public String getAcPassword() {
        return acPassword;
    }

    public void setAcPassword(String acPassword) {
        this.acPassword = acPassword;
    }

    public String getAddAppInfoURL() {
        return addAppInfoURL;
    }

    public void setAddAppInfoURL(String addAppInfoURL) {
        this.addAppInfoURL = addAppInfoURL;
    }

    public String getAddDeviceInfoURL() {
        return addDeviceInfoURL;
    }

    public void setAddDeviceInfoURL(String addDeviceInfoURL) {
        this.addDeviceInfoURL = addDeviceInfoURL;
    }
}
