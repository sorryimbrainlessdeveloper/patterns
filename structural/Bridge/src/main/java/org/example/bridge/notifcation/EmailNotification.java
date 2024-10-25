package org.example.bridge.notifcation;

import org.example.bridge.service.NotificationSender;import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification extends Notification {

    public EmailNotification(@Qualifier("emailSender") NotificationSender sender) {
        super(sender);
    }

    @Override
    public void send(String message) {
        sender.sendNotification("Email Notification: " + message);
    }
}
