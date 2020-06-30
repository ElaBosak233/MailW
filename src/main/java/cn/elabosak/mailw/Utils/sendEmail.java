package cn.elabosak.mailw.Utils;

import cn.elabosak.mailw.Main;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class sendEmail {

    static Main plugin;

    public static String myEmailAccount;
    static {
        assert false;
        myEmailAccount = plugin.getConfig().getString("MainSettings.Email");
    }
    public static String myEmailPassword = plugin.getConfig().getString("MainSettings.Passwd");
    public static String myEmailSMTPHost = plugin.getConfig().getString("MainSettings.Smtp");
    public static String smtpPort = plugin.getConfig().getString("MainSettings.Port");

    public static boolean send(String receiveMailAccount, String sender, String subject, String content) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        // 3. 创建一封邮件
        Message message = null;
        try {
            message = createMimeMessage(session, myEmailAccount, receiveMailAccount, sender, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = null;
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return false;
        }
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        try {
            transport.connect(myEmailAccount, myEmailPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        try {
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        // 7. 关闭连接
        try {
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String sender, String subject, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sender, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        //ToDo 是否有所可改进之处？
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

}
