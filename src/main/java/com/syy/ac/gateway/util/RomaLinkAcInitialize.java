package com.syy.ac.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.model.AgentConfig;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RomaLinkAcInitialize {

    private final Logger logger = LoggerFactory.getLogger(HttpsFileUtil.class);
    private String acBaseUrl;
    private AgentConfig config = null;
    private String token;

    public RomaLinkAcInitialize(AgentConfig config) {
        this.config = config;
        acBaseUrl = "https://" + config.getAcHost() + ":" + config.getAcPort();
        this.token = this.getAcToken();
        logger.info("ROMA对接AC------获取Token值为：{}", token);
        logger.info("ROMA对接AC初始化成功....");
    }

    private String addRomaApp(){
        String url = acBaseUrl+config.getAddAppInfoURL();
        String result;
        JSONObject params = new JSONObject();
        params.put("userName", config.getAcUserName());
        params.put("password", config.getAcPassword());
        return "";
    }

    private String getAcToken() {
        String url = acBaseUrl + config.getAcTokenURL();
        JSONObject params = new JSONObject();
        params.put("userName", config.getAcUserName());
        params.put("password", config.getAcPassword());
        logger.info("AC获取Token请求地址：{}", url);
        logger.info("AC获取Token请求参数：{}", params.toString());
        JSONObject result = JSONObject.parseObject(HttpClientUtil.doPost(url, params));
        logger.info("AC获取Token请求结果为：{}", result);
        return result.getJSONObject("data").getString("token_id");
    }
}
