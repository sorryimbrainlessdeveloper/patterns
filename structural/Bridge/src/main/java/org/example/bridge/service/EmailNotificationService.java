package org.example.bridge.service;

import org.example.bridge.notifcation.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService extends Notification {

    public EmailNotificationService(@Qualifier("emailSender") NotificationSender sender) {
        super(sender);
    }

    @Override
    public void send(String message) {
        sender.sendNotification("Email Notification: " + message);
    }
}
