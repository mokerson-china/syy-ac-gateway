package com.syy.ac.gateway.config;

import com.alibaba.fastjson.JSONObject;

public class AgileControllerFileConfig {
    private String name;
    private String size;
    private String digestAlgorithm;
    private String digest;
    private String signatureCheck;
    private String transferMode;
    private String fileServerAddress;
    private String fileServerPort;
    private String fileDirectory;
    private String authenticationMethod;
    private String caAuthenticationMethod;
    private String sliceEnable;

    public AgileControllerFileConfig(JSONObject file) {
        name = file.getString("name");
        size = file.getString("size");
        digestAlgorithm = file.getString("digestAlgorithm");
        digest = file.getString("digest");
        signatureCheck = file.getString("signatureCheck");
        transferMode = file.getString("transferMode");
        fileServerAddress = file.getString("fileserverAddress");
        fileServerPort = file.getString("fileserverPort");
        fileDirectory = file.getString("fileDirectory");
        authenticationMethod = file.getString("authenticationMethod");
        caAuthenticationMethod = file.getString("caAuthenticationMethod");
        sliceEnable = file.getString("sliceEnable");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSignatureCheck() {
        return signatureCheck;
    }

    public void setSignatureCheck(String signatureCheck) {
        this.signatureCheck = signatureCheck;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getFileServerAddress() {
        return fileServerAddress;
    }

    public void setFileServerAddress(String fileServerAddress) {
        this.fileServerAddress = fileServerAddress;
    }

    public String getFileServerPort() {
        return fileServerPort;
    }

    public void setFileServerPort(String fileServerPort) {
        this.fileServerPort = fileServerPort;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getCaAuthenticationMethod() {
        return caAuthenticationMethod;
    }

    public void setCaAuthenticationMethod(String caAuthenticationMethod) {
        this.caAuthenticationMethod = caAuthenticationMethod;
    }

    public String getSliceEnable() {
        return sliceEnable;
    }

    public void setSliceEnable(String sliceEnable) {
        this.sliceEnable = sliceEnable;
    }
}