package com.syy.ac.gateway.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.model.AgentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomaLinkAcInitialize {

    private final Logger logger = LoggerFactory.getLogger(HttpsFileUtil.class);
    private final String acBaseUrl;
    private final AgentConfig config;
    private Map<String, String> header;

    public RomaLinkAcInitialize(AgentConfig config) {
        this.config = config;
        acBaseUrl = "https://" + config.getAcHost() + ":" + config.getAcPort();
        this.setRequestToken(0);
        // AC增加ROMA应用
        this.addRomaApp();
        // AC增加ROMA设备档案信息
        this.addRomaDevice();
        logger.info("ROMA对接AC初始化成功....");
    }

    private void addRomaDevice() {
        String url = acBaseUrl + config.getAddDeviceInfoURL();
        JSONObject params = new JSONObject();
        List<Map<String, String>> devices = new ArrayList<>();
        // ROMA Device信息
        Map<String, String> appInfo = new HashMap<>(8);
        appInfo.put("esn", config.getDeviceEsn());
        appInfo.put("name", config.getDeviceName());
        appInfo.put("type", config.getDeviceType());
        appInfo.put("description", config.getDeviceDescription());
        appInfo.put("thirdPartyClientId", config.getDeviceThirdPartyClientId());
        appInfo.put("thirdPartyAppId", config.getDeviceThirdPartyAppId());
        devices.add(appInfo);
        params.put("devices",devices);
        logger.info("ROMA对接AC-----增加第三方设备档案信息——————请求URL为：{}",url);
        logger.info("ROMA对接AC-----增加第三方设备档案信息——————请求参数为：{}",params.toString());
        this.setRequestToken(1);
        JSONObject result = JSONObject.parseObject(HttpClientUtil.doPost(url, params, header));
        logger.info("ROMA对接AC-----增加第三方设备档案信息-----请求成功结果为：{}",result);
    }

    /**
     * 根据不同的请求类型，设置不同版本的Token获取方式
     * @param requestType   请求类型
     */
    private void setRequestToken(int requestType) {
        String token;
        if(requestType == 0){
            token = this.getAcToken();
        }else{
            token = this.getAcTokenAcIot();
        }
        logger.info("ROMA对接AC------获取Token值为：{}", token);
        header = new HashMap<>(3);
        header.put("X-ACCESS-TOKEN", token);
        logger.info("ROMA对接AC接口请求头设置成功");
    }

    private void addRomaApp() {
        String url = acBaseUrl + config.getAddAppInfoURL();
        JSONObject params = new JSONObject();
        List<Map<String, String>> apps = new ArrayList<>();
        // ROMA APP信息
        Map<String, String> appInfo = new HashMap<>(6);
        appInfo.put("id", config.getRomaAppId());
        appInfo.put("key", config.getRomaAppId());
        // 添加到数组中
        apps.add(appInfo);
        // 添加到apps参数中
        params.put("apps", apps);
        logger.info("ROMA对接AC-----增加APP信息——————请求URL为：{}",url);
        logger.info("ROMA对接AC-----增加APP信息——————请求参数为：{}",params.toString());
        JSONObject result = JSONObject.parseObject(HttpClientUtil.doPost(url, params, header));
        logger.info("ROMA对接AC-----增加APP信息-----请求成功");
        JSONObject data = result.getJSONObject("data");
        JSONArray failedIds = data.getJSONArray("failedIds");
        for (int i = 0, size = failedIds.size(); i < size; i++) {
            JSONObject temp = failedIds.getJSONObject(i);
            logger.info("ROMA对接AC-----增加APP请求成功，请求返回信息：{}，增加APPID为：{}", temp.getString("errmsg"), temp.getString("id"));
        }
    }

    /**
     * 第一种AC鉴权方式
     * @return  获取的Token值
     */
    private String getAcToken() {
        String url = acBaseUrl + config.getAcTokenURL();
        JSONObject params = new JSONObject();
        params.put("userName", config.getAcUserName());
        params.put("password", config.getAcPassword());
        logger.info("AC获取Token请求地址：{}", url);
        logger.info("AC获取Token请求参数：{}", params.toString());
        JSONObject result = JSONObject.parseObject(HttpClientUtil.doPost(url, params));
        logger.info("AC获取Token请求成功-------------------------------");
        return result.getJSONObject("data").getString("token_id");
    }

    /**
     * 第二种AC鉴权的方式
     * @return 获取的Token值
     */
    private String getAcTokenAcIot() {
        String url = acBaseUrl + config.getAcTokenURLAcIot();
        JSONObject params = new JSONObject();
        params.put("userName", config.getAcUserName());
        params.put("password", config.getAcPassword());
        logger.info("AC获取Token请求地址：{}", url);
        logger.info("AC获取Token请求参数：{}", params.toString());
        JSONObject result = JSONObject.parseObject(HttpClientUtil.doPost(url, params));
        logger.info("AC获取Token请求成功-------------------------------");
        return result.getJSONObject("data").getString("token_id");
    }
}
