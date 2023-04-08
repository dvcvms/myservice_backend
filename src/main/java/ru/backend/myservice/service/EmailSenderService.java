package ru.backend.myservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.backend.myservice.model.EmailBody;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailBody emailBody) { // TODO: send emails in a separate thread
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailBody.getToEmail());
        message.setText(emailBody.getBody());
        message.setSubject(emailBody.getSubject());

        javaMailSender.send(message);
    }
}
