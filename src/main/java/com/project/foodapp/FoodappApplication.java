package com.project.foodapp;

import javax.management.Notification;

import org.aspectj.weaver.ast.Not;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.project.foodapp.email_notifications.dtos.NotificationDTO;
import com.project.foodapp.email_notifications.services.NotificationService;
import com.project.foodapp.enums.NotificationType;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableAsync
// @RequiredArgsConstructor

public class FoodappApplication {

    // private final NotificationService notificationService;

    public static void main(String[] args) {
        SpringApplication.run(FoodappApplication.class, args);
    }

    // @Bean
    // CommandLineRunner runner() {
    // return args -> {
    // // Test email notification on startup

    // NotificationDTO notificationDTO = NotificationDTO.builder()
    // .recipient("harlin7814@gmail.com")
    // .subject("Test Notification")
    // .body("This is a test notification.")
    // .type(NotificationType.EMAIL)
    // .isHtml(false)
    // .build();
    // notificationService.sendEmail(notificationDTO);

    // };

    // }
}
