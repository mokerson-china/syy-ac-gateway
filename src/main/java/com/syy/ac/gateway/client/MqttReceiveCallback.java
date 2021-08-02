package com.syy.ac.gateway.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syy.ac.gateway.IotAgent;
import com.syy.ac.gateway.config.AgileControllerFileConfig;
import com.syy.ac.gateway.message.Containers;
import com.syy.ac.gateway.message.RegisterResultMessage;
import com.syy.ac.gateway.util.HttpsFileUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * MQTT协议订阅Topic接受到消息处理
 *
 * @author TanGuozheng
 */
public class MqttReceiveCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttReceiveCallback.class);
    private static final CreateMessage createMsg = new CreateMessage();
    private static String keepAliveMessageId = UUID.randomUUID().toString();

    public static String uploadFile( String url,String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        // String url = UPLOAD_URL.replace("ACCESS_TOKEN",
        // accessToken).replace("TYPE",type);
        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes(StandardCharsets.UTF_8);

        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8);// 定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return result;
    }

    /**
     * 创建线程，持续维持网关在AC上的心跳
     */
    public static void createTimerKeepAlive() {
        final long[] countKeep = {0};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                countKeep[0]++;
                MyMqttClient.publishMessage(IotAgent.config.getPubKeepaliveEventReply(), createMsg.getKeepAlive(keepAliveMessageId, "KeepAlive"));
                logger.info("成功发起第 {} 次心跳维持。。。。。。", countKeep[0]);
            }
        }, 0, 120000);
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    /**
     * 订阅Topic接收消息处理
     *
     * @param topic   Topic名称
     * @param message 消息内容
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info("————收到Topic：{}\n消息内容为：————\n{}", topic, message);
        JSONObject messages = JSONObject.parseObject(message.toString());
        String method = messages.getString("method");
        String messageId = messages.getString("messageId");

        // Topic: /{Version}/{DeviceId}/login/get处理
        // 设备注册回复内容，返回设备消息
        if ("DeviceState".equals(method)) {
            // 返回设备的状态数据
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getLoginGetReplyMessage(messageId, method));
        } else if ("DeviceRegister".equals(method)) {
            JSONObject params = messages.getJSONObject("params");
            if ("true".equals(params.getString("result"))) {
                // 设备注册结果回复
                MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));
                // 创建线程，持续保持设备上线
                createTimerKeepAlive();
            } else {
                logger.info("注册失败，失败原因为：{}", RegisterResultMessage.valueOf(params.getString("failReason")).getDescription());
            }
        } else if ("ContainerStatus".equals(method)) {
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getVirtualizationGetRep(messageId, method));
        } else if ("ContainerDownload".equals(method)) {
            logger.info("------------------------------------------------收到下发文件的内容：{}", message);

            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));
            this.fileDownload(messages);
        } else if ("ContainerDownloadStatus".equals(method)) {
            // 回复查询文件下载消息
            JSONArray fileNames = messages.getJSONObject("params").getJSONArray("files");
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getDownloadFileStatus(fileNames, messageId, method));

        } else if ("ContainerStorageMedia".equals(method)) {
            if (topic.contains("virtualization/get")) {
                // 查看容器可设置的工作目录
                MyMqttClient.publishMessage(topic + "/reply", createMsg.getContainerStorageMedia(messageId, method));
            }
        } else if ("LogfileUpload".equals(method)) {
            // 上传日志文件
            logger.info("======================================\n\n\n");
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));

            JSONArray files = messages.getJSONObject("params").getJSONArray("files");
            for (int i = 0, j = files.size(); i < j; i++) {
                JSONObject file = files.getJSONObject(i);
                // 初始化文件下载对象
                AgileControllerFileConfig fileConfig = new AgileControllerFileConfig(file);
                String url = fileConfig.getTransferMode() + "://" + fileConfig.getFileServerAddress() + ":" +
                        fileConfig.getFileServerPort() + fileConfig.getFileDirectory();
                try {
                    logger.info("开始执行上传文件，上传地址：{}", url);
                    uploadFile(url, "D:\\TEST\\test123.tar");
                    logger.info("请求成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("下载执行完成，下载地址为：");
            }

           /* String url = "https://172.18.2.116:1443/iotcenter/file-manager-service/v1/file/manager/upload";
            Map<String, String> map = new HashMap<>(4);
            File file = new File("D:\\TEST\\IOTINFO_2.tar");
            map.put("fileFolder", String.valueOf(UUID.randomUUID()).replace("-", ""));
            map.put("uploadType", "1");*/


        } else if ("ContainerInstall".equals(method)) {
            // 容器安装
            Containers containers = new Containers(messages);
            IotAgent.config.getContainers().add(containers);
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));

            // 5秒后返回安装容器内容
            delayedPublishMsg(5000, IotAgent.config.getCustomTopicList("virtualization/event", "virtualization"),
                    createMsg.getContainerInstallNotify("ContainerInstallNotify", containers));
        } else if ("ShellScriptLoad".equals(method)) {
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));
            this.fileDownload(messages);
        } else if ("ContainerStop".equals(method)) {
            // 停止容器
            String name = messages.getJSONObject("params").getString("containerName");
            String containerHyperv = messages.getJSONObject("params").getString("containerHyperv");
            // 删除容器
            Containers temp = null;
            List<Containers> containersList = IotAgent.config.getContainers();
            if (containersList != null) {
                for (Containers containers : containersList) {
                    if (name.equals(containers.getName()) && containerHyperv.equals(containers.getHyperv())) {
                        temp = containers;
                        containersList.remove(temp);
                        break;
                    }
                }
            }
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));

            delayedPublishMsg(5000, IotAgent.config.getCustomTopicList("virtualization/event", "virtualization"),
                    createMsg.getContainerInstallNotify("ContainerInstallNotify", temp));
        } else if ("ContainerRestore".equals(method)) {
            // 还原容器
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getMethodDeviceInfo(messageId, method));
        } else if ("ShellScriptLoadStatus".equals(method)) {
            JSONArray fileNames = messages.getJSONObject("params").getJSONArray("files");
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getDownloadShellStatus(fileNames, messageId, method));
        } else if("LogfileUploadStatus".equals(method)){
            JSONArray fileNames = messages.getJSONObject("params").getJSONArray("files");
            MyMqttClient.publishMessage(topic + "/reply", createMsg.getDownloadShellStatus(fileNames, messageId, method));
        }else {
            if (method == null || "".equals(method)) {
                method = messages.getString("type");
                if ("KeepAlive".equals(method)) {
                    logger.info("####心跳维持成功#####");
                    keepAliveMessageId = messages.getString("messageId");
                }
            } else {
                logger.error("--------------------------------{}暂未收录该方法，请和管理员联系--------------------------------", method);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void fileDownload(JSONObject messages) {
        JSONArray files = messages.getJSONObject("params").getJSONArray("files");
        for (int i = 0, j = files.size(); i < j; i++) {
            JSONObject file = files.getJSONObject(i);
            // 初始化文件下载对象
            AgileControllerFileConfig fileConfig = new AgileControllerFileConfig(file);
            String url = fileConfig.getTransferMode() + "://" + fileConfig.getFileServerAddress() + ":" +
                    fileConfig.getFileServerPort() + fileConfig.getFileDirectory() + fileConfig.getName();
            try {
                logger.info("开始执行下载，下载地址：{}", url);
                HttpsFileUtil.download(url, null, IotAgent.config.getDownloadPath() + fileConfig.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("下载执行完成");
        }
    }

    private void delayedPublishMsg(long millis, String topic, JSONObject msg) {
        new Thread(() -> {
            // 创建一个新线程用于反馈容器启动情况
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyMqttClient.publishMessage(topic, msg.toJSONString());
        }).start();
    }

    private void startContainer() {

    }
}