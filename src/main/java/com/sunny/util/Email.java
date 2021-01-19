package com.sunny.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public final class Email {

    private final String smtpHost;

    private final Address sendAddress;

    private final String password;

    private final List<Address> toAddress = new ArrayList<>();

    private String title;

    private String body;

    private final List<File> files = new ArrayList<>();

    public Email(String smtpHost, Address sendAddress, String password) {
        this.smtpHost = smtpHost;
        this.sendAddress = sendAddress;
        this.password = password;
    }

    /**
     * 添加收件人
     *
     * @param address address
     * */
    public void addToAddress(Address address) {
        this.toAddress.add(address);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * */
    public void setTitle(String title) {
        this.title =title;
    }

    /**
     * 设置正文
     *
     * @param body body
     * */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 添加附件
     *
     * @param file 附件
     * */
    public void addFile(File file) {
        this.files.add(file);
    }

    public void send() {
        if (this.toAddress.size() == 0) {
            return;
        }
        if (this.title == null || this.title.equals("")) {
            return;
        }

        Transport transport = null;

        try {
            Properties p = new Properties();
            p.setProperty("mail.transport.protocol", "smtp");
            p.setProperty("mail.smtp.host", this.smtpHost);
            p.setProperty("mail.smtp.localhost", "localhost");
            p.setProperty("mail.smtp.auth", "true");
            Session session = Session.getInstance(p);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.sendAddress.address, this.sendAddress.name, "UTF-8"));
            javax.mail.Address[] addresses = new javax.mail.Address[this.toAddress.size()];
            for (int i = 0; i < this.toAddress.size(); i++) {
                Address address = this.toAddress.get(i);
                addresses[i] = new InternetAddress(address.address, address.name, "UTF-8");
            }
            message.addRecipients(MimeMessage.RecipientType.TO, addresses);
            message.setSubject(this.title, "UTF-8");
            message.setSentDate(new Date());

            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart body = new MimeBodyPart();
            body.setContent(this.body, "text/html;charset=UTF-8");
            mimeMultipart.addBodyPart(body);
            for (File file : this.files) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                DataSource dataSource = new FileDataSource(file.getAbsolutePath());
                mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                mimeBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
                mimeMultipart.addBodyPart(mimeBodyPart);
                mimeMultipart.setSubType("mixed");
            }
            message.setContent(mimeMultipart);

            message.saveChanges();
            transport = session.getTransport();
            transport.connect(this.sendAddress.address, this.password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Address {

        private final String name;

        private final String address;

        public Address(String name, String address) {
            this.name = name;
            this.address = address;
        }

    }

    public static void main(String[] args) {
        Email email = new Email("smtp.163.com", new Email.Address("zhoujun", "13875057583@163.com"), "RYKLZAKTLZWSBUIP");

        // 设置收件人
        email.addToAddress(new Address("syf", "yfsun@easipass.com"));

        // 设置标题
        email.setTitle("这是标题");

        // 设置主体
        email.setBody("这是主体");

        // 添加文件


        // 发送
        email.send();
    }

}