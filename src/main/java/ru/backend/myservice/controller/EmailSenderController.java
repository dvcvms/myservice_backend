package ru.backend.myservice.controller;


import ru.backend.myservice.model.EmailBody;
import ru.backend.myservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailBody emailBody) {
        emailSenderService.sendEmail(emailBody);
    }
}
