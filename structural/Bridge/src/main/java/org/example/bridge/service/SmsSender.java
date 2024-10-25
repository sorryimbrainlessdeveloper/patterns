package org.example.bridge.service;

import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending SMS with message: " + message);
    }
}
