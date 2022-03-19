package uz.pdp.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

       private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMailSender(String toEmail,String subject,String body) throws MailException {

        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom("javohirbekrakhimov@gmail.com");
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
       simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);

    }
}
