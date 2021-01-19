package com.sunny.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendMailUtil {

    public static void sendCode(String toMailAddress, String title, String text, Integer code) throws GeneralSecurityException, MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");

        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
        mailSSLSocketFactory.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

        // 创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1164806928@qq.com", "agrmjstkksrcfdif");
            }
        });

        //开启debug模式
        session.setDebug(false);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect("smtp.qq.com", "1164806928@qq.com", "agrmjstkksrcfdif");

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("1164806928@qq.com"));

        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toMailAddress));

        //邮件标题
        mimeMessage.setSubject(title);

        //邮件内容
        text = "欢迎注册绑定邮箱，您的验证码是：";
        mimeMessage.setContent(text + code, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();
    }

//    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
//        SendMailUtil.sendCode("yfsun@easipass.com","test","123456");
//    }
}

