package org.example.bridge.notifcation;

import org.example.bridge.service.NotificationSender;

public abstract class Notification {
    public NotificationSender sender;

    public Notification(NotificationSender sender) {
        this.sender = sender;
    }

    public abstract void send(String message);
}
