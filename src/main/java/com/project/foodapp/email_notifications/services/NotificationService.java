package com.project.foodapp.email_notifications.services;

import com.project.foodapp.email_notifications.dtos.NotificationDTO;

public interface NotificationService {

    void sendEmail(NotificationDTO notificationDTO);
}
