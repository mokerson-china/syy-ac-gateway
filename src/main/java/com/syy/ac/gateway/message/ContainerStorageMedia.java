package com.syy.ac.gateway.message;

/**
 *
 * @author TanGuozheng
 *
 */
public class ContainerStorageMedia {

    private String path = "/root/mqtt-agent-ar502/download";
    private String desc = "Usr Disk";
    private int size = 204800;
    private int active = 1;
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setActive(int active) {
        this.active = active;
    }
    public int getActive() {
        return active;
    }

}