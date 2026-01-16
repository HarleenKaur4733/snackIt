package com.project.foodapp.email_notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.email_notifications.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
