package com.syy.ac.gateway.model;

import java.util.Properties;

public class AgentConfig {
    private String connectorHost;
    private String connectorPort;
    private String gatewayId;
    private String clientId;
    private String connectorUser;
    private String connectorPassword;
    private int handlerThreadNum = 4;
    private boolean gatewayDevice = true;
    private String backupFolder = "";
    private boolean topicUseIdentifier = true;
    private String deviceIdentifier;
    private Properties props;
    private boolean sslEnable;
    private String sslVersion;
    private String sslPort;
    private String caCertificateFile;
    private int maxMessageSize = 524288;

    public AgentConfig(Properties props) {
        this.connectorHost = props.getProperty("connectorHost");
        this.connectorPort = props.getProperty("connectorPort");
        this.gatewayId = props.getProperty("gatewayId");
        this.connectorUser = props.getProperty("connectorUser");
        this.connectorPassword = props.getProperty("connectorPassword");
        this.clientId = props.getProperty("clientId");
        this.handlerThreadNum = Integer.parseInt(props.getProperty("handlerThreadsNum", "4"));
        this.backupFolder = props.getProperty("backupFolder");
        this.gatewayDevice = Boolean.parseBoolean(props.getProperty("isGatewayDevice", "true"));
        this.topicUseIdentifier = Boolean.parseBoolean(props.getProperty("topicUseIdentifier", "true"));
        this.deviceIdentifier = props.getProperty("deviceIdentifier");
        this.sslEnable = Boolean.parseBoolean(props.getProperty("sslEnable", "false"));
        if (this.sslEnable) {
            this.sslVersion = props.getProperty("sslVersion", "TLSv1.2");
            this.sslPort = props.getProperty("sslPort", "8443");
            this.caCertificateFile = props.getProperty("caCertificateFile");
        }

        this.props = props;
    }

    public String getConnectorHost() {
        return this.connectorHost;
    }

    public void setConnectorHost(String connectorHost) {
        this.connectorHost = connectorHost;
    }

    public String getConnectorPort() {
        return this.connectorPort;
    }

    public void setConnectorPort(String connectorPort) {
        this.connectorPort = connectorPort;
    }

    public String getGatewayId() {
        return this.gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getConnectorUser() {
        return this.connectorUser;
    }

    public void setConnectorUser(String connectorUser) {
        this.connectorUser = connectorUser;
    }

    public String getConnectorPassword() {
        return this.connectorPassword;
    }

    public void setConnectorPassword(String connectorPassword) {
        this.connectorPassword = connectorPassword;
    }

    public String getProperty(String propertyName) {
        return this.props.getProperty(propertyName);
    }

    public String toString() {
        return "AgentConfig [connectorHost=" + this.connectorHost + ", connectorPort=" + this.connectorPort + ", gatewayId=" + this.gatewayId + ", connectorUser=" + this.connectorUser + ", connectorPassword=" + this.connectorPassword + "]";
    }

    public int getHandlerThreadNum() {
        return this.handlerThreadNum;
    }

    public void setHandlerThreadNum(int handlerThreadNum) {
        this.handlerThreadNum = handlerThreadNum;
    }

    public String getBackupFolder() {
        return this.backupFolder;
    }

    public void setBackupFolder(String backupFolder) {
        this.backupFolder = backupFolder;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isGatewayDevice() {
        return this.gatewayDevice;
    }

    public void setGatewayDevice(boolean gatewayDevice) {
        this.gatewayDevice = gatewayDevice;
    }

    public boolean isTopicUseIdentifier() {
        return this.topicUseIdentifier;
    }

    public void setTopicUseIdentifier(boolean topicUseIdentifier) {
        this.topicUseIdentifier = topicUseIdentifier;
    }

    public String getDeviceIdentifier() {
        return this.deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public boolean isSslEnable() {
        return this.sslEnable;
    }

    public void setSslEnable(boolean sslEnable) {
        this.sslEnable = sslEnable;
    }

    public String getSslVersion() {
        return this.sslVersion;
    }

    public void setSslVersion(String sslVersion) {
        this.sslVersion = sslVersion;
    }

    public String getSslPort() {
        return this.sslPort;
    }

    public void setSslPort(String sslPort) {
        this.sslPort = sslPort;
    }

    public String getCaCertificateFile() {
        return this.caCertificateFile;
    }

    public void setCaCertificateFile(String caCertificateFile) {
        this.caCertificateFile = caCertificateFile;
    }

    public int getMaxMessageSize() {
        return this.maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }
}
