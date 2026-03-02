package com.codetrace.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.user-registered:user.registered}")
    private String registrationTopic;

    @Value("${app.kafka.topics.user-login:user.login}")
    private String loginTopic;

    @Bean
    public NewTopic userRegisteredTopic() {
        return TopicBuilder.name(registrationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userLoginTopic() {
        return TopicBuilder.name(loginTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

