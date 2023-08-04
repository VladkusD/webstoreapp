package bg.webapp.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(String email,StringBuilder items) {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(email);
        mailMessage.setText("You have ordered the following items "+items);
        mailMessage.setSubject("Your order was successfully registered");

        javaMailSender.send(mailMessage);
    }
}
