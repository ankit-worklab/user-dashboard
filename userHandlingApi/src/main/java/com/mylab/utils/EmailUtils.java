package com.mylab.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to , String sub , String body){
        boolean isMailSend = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(sub);
            helper.setText(body,true);
            mailSender.send(mimeMessage);
            isMailSend=true;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return  isMailSend;
    }
}
