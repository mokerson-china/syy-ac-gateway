package com.syy.ac.gateway.message;

import com.alibaba.fastjson.JSONObject;

public class ContainersVolumes {
    private String path;
    private Integer visible;
    private Integer size;

    public ContainersVolumes(JSONObject jsonObject) {
        this.path = jsonObject.getString("path");
        this.visible = jsonObject.getInteger("visible");
        this.size = jsonObject.getInteger("size");
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
