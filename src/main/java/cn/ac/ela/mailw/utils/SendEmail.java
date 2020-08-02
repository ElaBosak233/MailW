package cn.ac.ela.mailw.utils;

import cn.ac.ela.mailw.api.MailWApi;
import cn.ac.ela.mailw.sql.MailWSettings;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

/**
 * @author ElaBosak & CSDN & CnBlog
 */
public class SendEmail {

    public static String myEmailAccount;
    static {
        try {
            Connection con = MailWApi.getConnection();
            myEmailAccount = MailWSettings.selectEmail(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String myEmailPassword;
    static {
        try {
            Connection con = MailWApi.getConnection();
            myEmailPassword = Base64Run.decoder(MailWSettings.selectPasswd(con));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String myEmailSMTPHost;
    static {
        try {
            Connection con = MailWApi.getConnection();
            myEmailSMTPHost = MailWSettings.selectSmtp(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String smtpPort;
    static {
        try {
            Connection con = MailWApi.getConnection();
            smtpPort = MailWSettings.selectPort(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        session.setDebug(false);
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
        message.setFrom(new InternetAddress(sendMail, sender, "utf-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "utf-8"));
        // 4. Subject: 邮件主题
        message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content,"text/html;charset=utf-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

}
