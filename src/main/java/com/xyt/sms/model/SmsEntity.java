package com.xyt.sms.model;

import java.util.Date;

/**
 * @author lmh
 * @Title:
 * @date 2019/9/18 0018
 */
public class SmsEntity {
    /**
     * 主键自增
     */
    private String id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String mobileCode;
    /**
     * 发送时间
     */
    private Date sendtime;
    /**
     * 发送地址ip
     */
    private String sendAddressIp;
    /**
     * 发送结果状态
     */
    private Integer sendState;
    /**
     * 短信模板Code
     */
    private String smsTemplete;
    /**
     * 业务类型
     */
    private String vouType;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getMobileCode(){
        return mobileCode;
    }

    public void setMobileCode(String mobileCode){
        this.mobileCode = mobileCode;
    }

    public Date getSendtime(){
        return sendtime;
    }

    public void setSendtime(Date sendtime){
        this.sendtime = sendtime;
    }

    public String getSendAddressIp(){
        return sendAddressIp;
    }

    public void setSendAddressIp(String sendAddressIp){
        this.sendAddressIp = sendAddressIp;
    }

    public Integer getSendState(){
        return sendState;
    }

    public void setSendState(Integer sendState){
        this.sendState = sendState;
    }

    public String getSmsTemplete(){
        return smsTemplete;
    }

    public void setSmsTemplete(String smsTemplete){
        this.smsTemplete = smsTemplete;
    }

    public String getVouType() {
        return vouType;
    }

    public void setVouType(String vouType) {
        this.vouType = vouType;
    }
}
