
package com.example.auth_system.service;

import com.example.auth_system.dto.auth.RegisterRequest;
import com.example.auth_system.dto.auth.RegisterResponse;
import com.example.auth_system.repository.UserRepository;
import com.example.auth_system.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(
                email.trim().toLowerCase(Locale.ROOT)
        );
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(
                email.trim().toLowerCase(Locale.ROOT)
        );
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String normalizedEmail =
                request.email().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists"
            );
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(normalizedEmail);
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }
}
