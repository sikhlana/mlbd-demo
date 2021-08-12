package name.saifmahmud.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    private final JavaMailSender sender;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@saifmahmud.name");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        sender.send(message);
    }
}
