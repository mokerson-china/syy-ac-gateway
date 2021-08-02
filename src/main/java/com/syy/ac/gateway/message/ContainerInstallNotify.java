package com.syy.ac.gateway.message;

/**
 * @author TanGuozheng
 */
public class ContainerInstallNotify {
    private String containerName;

    private int containerIndex;

    private String result;

    private String failReason;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setContainerName(String containerName){
        this.containerName = containerName;
    }
    public String getContainerName(){
        return this.containerName;
    }
    public void setContainerIndex(int containerIndex){
        this.containerIndex = containerIndex;
    }
    public int getContainerIndex(){
        return this.containerIndex;
    }
    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setFailReason(String failReason){
        this.failReason = failReason;
    }
    public String getFailReason(){
        return this.failReason;
    }
}
