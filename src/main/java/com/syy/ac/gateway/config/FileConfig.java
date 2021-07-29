package com.syy.ac.gateway.config;

import java.util.Properties;

public class FileConfig {
    private String fileHost;
    private String filePort;
    private String X_HW_ID;
    private String X_HW_APPKEY;
    private String uploadURL;
    private String downloadURL;
    private Properties props;
    private String downloadPath;

    public FileConfig(Properties props) {
        this.fileHost = props.getProperty("file.fileHost");
        this.filePort = props.getProperty("file.filePort");
        this.X_HW_ID = props.getProperty("file.X-HW-ID");
        this.X_HW_APPKEY = props.getProperty("file.X-HW-APPKEY");
        this.uploadURL = props.getProperty("file.uploadURL");
        this.downloadURL = props.getProperty("file.downloadURL");
        this.downloadPath = props.getProperty("file.downloadPath");
        this.props = props;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFileHost() {
        return fileHost;
    }

    public void setFileHost(String fileHost) {
        this.fileHost = fileHost;
    }

    public String getFilePort() {
        return filePort;
    }

    public void setFilePort(String filePort) {
        this.filePort = filePort;
    }

    public String getX_HW_ID() {
        return X_HW_ID;
    }

    public void setX_HW_ID(String x_HW_ID) {
        X_HW_ID = x_HW_ID;
    }

    public String getX_HW_APPKEY() {
        return X_HW_APPKEY;
    }

    public void setX_HW_APPKEY(String x_HW_APPKEY) {
        X_HW_APPKEY = x_HW_APPKEY;
    }

    public String getUploadURL() {
        return uploadURL;
    }

    public void setUploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }
}
