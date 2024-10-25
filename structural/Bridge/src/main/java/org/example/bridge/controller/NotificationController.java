package org.example.bridge.controller;

import lombok.RequiredArgsConstructor;
import org.example.bridge.notifcation.EmailNotification;
import org.example.bridge.notifcation.SmsNotification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailNotification emailNotification;
    private final SmsNotification smsNotification;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String message) {
        emailNotification.send(message);
        return "Email sent!";
    }

    @GetMapping("/sendSms")
    public String sendSms(@RequestParam String message) {
        smsNotification.send(message);
        return "SMS sent!";
    }
}
