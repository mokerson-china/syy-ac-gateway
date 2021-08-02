package com.syy.ac.gateway.config;

import com.syy.ac.gateway.message.Containers;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AgentConfig extends AgileControllerConfig {
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
    private String replaceVersion;
    private String replaceDeviceId;
    private List<String> customTopicList;
    private List<Containers> containers;

    /**
     * 设备管理Topic配置
     */
    private String[] subTopic;

    private String pubKeepaliveEventReply;
    private String topicVersion;
    private int maxMessageSize = 524288;

    public AgentConfig(Properties props) {
        super(props);
        this.connectorHost = props.getProperty("mqtt.connectorHost");
        this.connectorPort = props.getProperty("mqtt.connectorPort");
        this.gatewayId = props.getProperty("mqtt.gatewayId");
        this.connectorUser = props.getProperty("mqtt.connectorUser");
        this.connectorPassword = props.getProperty("mqtt.connectorPassword");
        this.clientId = props.getProperty("mqtt.clientId");
        this.handlerThreadNum = Integer.parseInt(props.getProperty("mqtt.handlerThreadsNum", "4"));
        this.backupFolder = props.getProperty("mqtt.backupFolder");
        this.customTopicList = Arrays.asList(props.getProperty("mqtt.custom.topic").split(";"));

        this.subTopic = props.getProperty("mqtt.sub.topic.topicList").split(";");
        this.pubKeepaliveEventReply = props.getProperty("mqtt.pub.topic.login.keepalive.event.reply");

        this.replaceVersion = props.getProperty("mqtt.topic.replaceVersion");
        this.replaceDeviceId = props.getProperty("mqtt.topic.replaceDeviceId");

        this.gatewayDevice = Boolean.parseBoolean(props.getProperty("mqtt.isGatewayDevice", "true"));
        this.topicUseIdentifier = Boolean.parseBoolean(props.getProperty("mqtt.topicUseIdentifier", "true"));
        this.deviceIdentifier = props.getProperty("mqtt.deviceIdentifier");
        this.topicVersion = props.getProperty("mqtt.topic.version");
        this.sslEnable = Boolean.parseBoolean(props.getProperty("mqtt.sslEnable", "false"));
        if (this.sslEnable) {
            this.sslVersion = props.getProperty("mqtt.sslVersion", "TLSv1.2");
            this.sslPort = props.getProperty("mqtt.sslPort", "8443");
            this.caCertificateFile = props.getProperty("mqtt.caCertificateFile");
        }
        this.props = props;

        // 替换自带内容，更新每个设备的Topic信息
        for(int i=0;i<subTopic.length;i++){
            subTopic[i] = subTopic[i].replace(replaceVersion, topicVersion).replace(replaceDeviceId, gatewayId);
        }
        pubKeepaliveEventReply = pubKeepaliveEventReply.replace(replaceVersion, topicVersion).replace(replaceDeviceId, gatewayId);

    }

    public List<Containers> getContainers() {
        return containers;
    }

    public void setContainers(List<Containers> containers) {
        this.containers = containers;
    }

    public List<String> getCustomTopicList() {
        return customTopicList;
    }

    public void setCustomTopicList(List<String> customTopicList) {
        this.customTopicList = customTopicList;
    }

    public String getPubKeepaliveEventReply() {
        return pubKeepaliveEventReply;
    }

    public void setPubKeepaliveEventReply(String pubKeepaliveEventReply) {
        this.pubKeepaliveEventReply = pubKeepaliveEventReply;
    }


    public String[] getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String[] subTopic) {
        this.subTopic = subTopic;
    }

    public String getReplaceVersion() {
        return replaceVersion;
    }

    public void setReplaceVersion(String replaceVersion) {
        this.replaceVersion = replaceVersion;
    }

    public String getReplaceDeviceId() {
        return replaceDeviceId;
    }

    public void setReplaceDeviceId(String replaceDeviceId) {
        this.replaceDeviceId = replaceDeviceId;
    }

    public String getTopicVersion() {
        return topicVersion;
    }

    public void setTopicVersion(String topicVersion) {
        this.topicVersion = topicVersion;
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

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void setProps(Properties props) {
        this.props = props;
    }
}
