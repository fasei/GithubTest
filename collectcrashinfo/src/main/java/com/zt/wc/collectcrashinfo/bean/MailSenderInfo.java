package com.zt.wc.collectcrashinfo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送者的实体(亲测163邮箱可以使用！！)
 * <p>
 * Created by 王超 on 2016/12/15.
 */
public class MailSenderInfo {
    // 发送邮件的服务器的IP和端口
    private String mailServerHost = "smtp.163.com";
    private String mailServerPort = "465";
    // 登陆邮件发送服务器的用户名和密码
    private String userName = "18513410245@163.com";
    private String password = "919536816a";
    // 邮件接收者的地址
    private List<String> toAddress;
    // 邮件主题
    private String subject = "Crash log";

    public MailSenderInfo() {
        toAddress = new ArrayList<>();
    }

    public MailSenderInfo(String mailServerHost, String mailServerPort, String userName, String password, List<String> toAddress, String subject) {
        this.mailServerHost = mailServerHost;
        this.mailServerPort = mailServerPort;
        this.userName = userName;
        this.password = password;
        this.toAddress = toAddress;
        this.subject = subject;

    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public MailSenderInfo setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
        return this;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public MailSenderInfo setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public MailSenderInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MailSenderInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public MailSenderInfo setToAddress(String... toAddress) {
        if (this.toAddress == null) {
            this.toAddress = new ArrayList<>();
        }
        for (String str : toAddress) {
            this.toAddress.add(str);
        }
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailSenderInfo setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public String toString() {
        return "MailSenderInfo{" +
                "mailServerHost='" + mailServerHost + '\'' +
                ", mailServerPort='" + mailServerPort + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", toAddress=" + toAddress +
                ", subject='" + subject + '\'' +
                '}';
    }
}
