package com.smartStore.project.EmailUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(String to,String subject,String text, List<String> list){
        SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
        simpleMailMessage.setFrom("allyjulo@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        if(list!=null&& list.size()>0){
            simpleMailMessage.setCc(getCcArray(list));
        }

        javaMailSender.send(simpleMailMessage);
    }
    private String [] getCcArray(List<String> ccList){
        String[] cc=new String[ccList.size()];
        for(int i=0; i<ccList.size();i++){
            cc[i]=ccList.get(i);
        }
        return cc;
    }

    public  void forgetMail (String to,String subject,String password)throws MessagingException {
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);

        messageHelper.setFrom("allyjulo@gmail.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        String htmlMessage="<p><b> Your login details for SmartStore </b><br><b>Email:<b>"+to+"<br><b> Password:<b>"+password+"<br><a " +
                "href=\"http://localhost:4200/\"> click here to login<a></p>";
        message.setContent(htmlMessage,"text/html");
        javaMailSender.send(message);

    }
}
