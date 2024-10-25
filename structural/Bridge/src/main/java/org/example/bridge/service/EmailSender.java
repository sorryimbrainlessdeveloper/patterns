package org.example.bridge.service;

import org.springframework.stereotype.Component;

@Component
public class EmailSender implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending Email with message: " + message);
    }
}
