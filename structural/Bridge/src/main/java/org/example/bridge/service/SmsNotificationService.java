package org.example.bridge.service;

import org.example.bridge.notifcation.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationService extends Notification {

    public SmsNotificationService(@Qualifier("smsSender") NotificationSender sender) {
        super(sender);
    }

    @Override
    public void send(String message) {
        sender.sendNotification("SMS Notification: " + message);
    }
}

