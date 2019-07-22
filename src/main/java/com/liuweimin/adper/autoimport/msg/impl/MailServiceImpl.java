package com.liuweimin.adper.autoimport.msg.impl;

import com.liuweimin.adper.autoimport.msg.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


/**
 * @author liuwm
 * @description
 * @date 7/8/2019 5:04 PM
 */
@Component
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender sender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendTextMailService(String subject, String content,String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            sender.send(message);
            log.info("邮件已经发送出去");
        } catch (Exception e) {
            log.error("邮件没有发送出去" + e);
        }

    }
}
