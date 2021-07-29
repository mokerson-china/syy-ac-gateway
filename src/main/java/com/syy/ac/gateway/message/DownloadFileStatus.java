package com.syy.ac.gateway.message;
/**
 * @author TanGuozheng
 */

public class DownloadFileStatus {


    private String name;
    private String status;
    private String type;
    private int percentage;
    private int errorCode;
    private String shellStatus;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    public int getPercentage() {
        return percentage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setShellStatus(String shellStatus) {
        this.shellStatus = shellStatus;
    }
    public String getShellStatus() {
        return shellStatus;
    }

}
