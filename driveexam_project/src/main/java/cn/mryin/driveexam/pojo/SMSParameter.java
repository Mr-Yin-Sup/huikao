package cn.mryin.driveexam.pojo;

import lombok.Data;

/**
 * @author MrYin
 * @date 2022/1/25
 */

public class SMSParameter {
    // 这里是第一步应用里面的 appId
    private int appId = 1400626318;
    //同理，密码
    private String appKey = "b36ec88e3e8f516feeb3b63d5a202206";
    // 这个其实可有可无，我就没用上
    private String[] phoneNumbers;
    // 模板 id 见上图
    private int templateId = 1289179;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }

    // 这里写签名的内容
    private String smsSign = "银先生的个人站";



}