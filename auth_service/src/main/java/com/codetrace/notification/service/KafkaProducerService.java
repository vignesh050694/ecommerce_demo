package com.codetrace.notification.service;

import com.codetrace.notification.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.user-registered:user.registered}")
    private String registrationTopic;

    @Value("${app.kafka.topics.user-login:user.login}")
    private String loginTopic;

    public void publishUserRegistration(UserDTO user) {
        Map<String, Object> message = new HashMap<>();
        message.put("username", user.getUsername());
        message.put("email", user.getEmail());
        message.put("event", "REGISTRATION");
        message.put("timestamp", LocalDateTime.now().toString());

        log.info("Publishing registration event for user: {}", user.getUsername());
        kafkaTemplate.send("user.registered", message);
    }

    public void publishUserLogin(String username) {
        Map<String, Object> message = new HashMap<>();
        message.put("username", username);
        message.put("event", "LOGIN");
        message.put("timestamp", LocalDateTime.now().toString());

        log.info("Publishing login event for user: {}", username);
        kafkaTemplate.send("user.login", message);
    }
}

