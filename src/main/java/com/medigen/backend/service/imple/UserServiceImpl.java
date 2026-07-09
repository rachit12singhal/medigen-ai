package com.medigen.backend.service.imple;

import com.medigen.backend.dto.request.LoginRequest;
import com.medigen.backend.dto.request.RegisterRequest;
import com.medigen.backend.dto.response.AuthResponse;
import com.medigen.backend.dto.response.UserResponse;
import com.medigen.backend.entity.User;
import com.medigen.backend.enums.Role;
import com.medigen.backend.repository.UserRepository;
import com.medigen.backend.security.JwtService;
import com.medigen.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.medigen.backend.dto.response.AuthResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with this email.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new AuthResponse(
                jwtService.generateToken(user.getEmail())
        );
    }
}