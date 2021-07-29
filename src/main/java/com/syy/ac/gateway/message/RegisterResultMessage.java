package com.syy.ac.gateway.message;

public enum RegisterResultMessage {
    enum0 ("0","internal error of the controller，表示控制器内部错误"),
    enum1 ("1","the controller does not obtain basic device information in the specified period or the device does not reply，表示控制器获取设备基本信息报文超时或设备返回应答报文失败。"),
    enum2 ("2","the structure of obtained basic device information is invalid，表示控制器获取到的设备基本信息报文结构不合法。"),
    enum3 ("3","the device is not added to the controller，表示设备未添加到控制器中。"),
    enum4 ("4","the license is unauthorized or expired，表示许可证未授权或已过期。"),
    enum5 ("5","controller node allocation fails，表示控制器节点分配失败。"),
    enum6 ("6","the device does not report the model，表示设备未上报型号。"),
    enum7 ("7","the model reported by the device does not match the ESN，表示设备上报的型号与ESN不匹配。"),
    enum8 ("8","the device does not report the MAC address，表示设备未上报MAC地址。"),
    enum9 ("9","the format of MAC address reported by the device is invalid，表示设备上报的MAC地址格式是非法的。"),
    enum200 ("200","the channel can not be established，表示通道无法建立。");
    private String code;
    private String description;

    public String getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }

    RegisterResultMessage(String code, String description){
        this.code = code;
        this.description = description;
    }

}
