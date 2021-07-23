package com.syy.ac.gateway.model;

import java.util.Properties;

public class AgileControllerConfig extends FileConfig{

    private String romaAppId;
    private String romaAppKey;
    private String acHost;
    private String acPort;
    private String acUserName;
    private String acPassword;
    private String acTokenURL;
    private String addAppInfoURL;
    private String addDeviceInfoURL;

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
