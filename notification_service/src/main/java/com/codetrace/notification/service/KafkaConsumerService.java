package com.codetrace.notification.service;

import com.codetrace.notification.dto.UserDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topics.user-registered:user.registered}", groupId = "notification-group", containerFactory = "userDtoKafkaListenerContainerFactory")
    public void consumeUserRegistered(UserDTO userDTO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", userDTO.getFirstName());
        variables.put("lastName", userDTO.getLastName());
        variables.put("email", userDTO.getEmail());
        variables.put("mobileNumber", userDTO.getMobileNumber());
        variables.put("username", userDTO.getUsername());
        try {
            emailService.sendWelcomeEmail(userDTO.getEmail(), variables);
        } catch (MessagingException e) {
            // Log error
            e.printStackTrace();
        }
    }
}
