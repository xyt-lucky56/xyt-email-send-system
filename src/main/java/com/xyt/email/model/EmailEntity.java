package com.xyt.email.model;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;

public class EmailEntity {

    /**
     * 收件人
     */
    private String emailTo;

    //群发收件人
    private List<InternetAddress> emailToUsers;

    /**
     * 服务器名
     */
    private String hostName;

    /**
     * 发件人邮箱用户名
     */
    private String emailUserName;

    /**
     * 发件人邮箱密码
     */
    private String passWord;

    /**
     * 发件人邮箱
     */
    private String emailFrom;

    /**
     * 邮件标题
     */
    private String emailSubject;

    /**
     * 文件内容
     */
    private String emailContent;

    /**
     * 邮件附件
     */
    private List<File> attachFiles;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getEmailUserName() {
        return emailUserName;
    }

    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public List<File> getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(List<File> attachFiles) {
        this.attachFiles = attachFiles;
    }

    public List<InternetAddress> getEmailToUsers() {
        return emailToUsers;
    }

    public void setEmailToUsers(List<InternetAddress> emailToUsers) {
        this.emailToUsers = emailToUsers;
    }
}
