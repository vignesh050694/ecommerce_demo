package com.codetrace.notification.service;

import com.codetrace.notification.domain.User;
import com.codetrace.notification.dto.UserDTO;
import com.codetrace.notification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public UserDTO registerUser(UserDTO userDto) {
        log.info("Registering user: {}", userDto.getUsername());

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
                .username(userDto.getUsername())
                .password(encryptionService.encryptPassword(userDto.getPassword()))
                .build();

        userRepository.save(user);

        // Notify Kafka
        kafkaProducerService.publishUserRegistration(userDto);

        // Clean password before returning
        userDto.setPassword(null);

        return userDto;
    }

    public boolean login(String username, String password) {
        log.info("Attempting login for user: {}", username);

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean isMatch = encryptionService.matches(password, user.getPassword());

            if (isMatch) {
                // Notify Kafka
                kafkaProducerService.publishUserLogin(username);
                return true;
            }
        }
        return false;
    }
}
