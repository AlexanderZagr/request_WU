package com.requst.wu.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SendGmail {

    final Logger logger = LoggerFactory.getLogger(SendGmail.class);
    private String to_email;


    @Autowired
    public SendGmail(@Value("${service.sp.email}") String to_email) {
        this.to_email = to_email;
        System.out.println("!!!"+to_email);
    }
    public String sendEmailWithAttachment(JavaMailSender javaMailSender,String subject,String content,String fileName) {
        StringBuilder resultSending=new StringBuilder();
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(to_email);
            helper.setSubject(subject);
            //true = text/html
            helper.setText(content, true);
            if (fileName != null) {
                Path path = Paths.get("./uploads/"+fileName);
                byte[] attachFilePath = Files.readAllBytes(path);
                helper.addAttachment(fileName, new ByteArrayResource(attachFilePath));
            }
            javaMailSender.send(msg);
            logger.info("Письмо отправлено : "+content+" email: "+to_email);
            resultSending.append("success");
        } catch (Exception e){
            logger.error(to_email+" Ошибка отправки письма: "+e+" ["+fileName+"]");
            resultSending.append("error");
        }
        return resultSending.toString();
}
}
