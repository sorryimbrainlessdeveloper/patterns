package org.example.bridge.notifcation;

import org.example.bridge.service.NotificationSender;import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SmsNotification extends Notification {

    public SmsNotification(@Qualifier("smsSender") NotificationSender sender) {
        super(sender);
    }

    @Override
    public void send(String message) {
        sender.sendNotification("SMS Notification: " + message);
    }
}

