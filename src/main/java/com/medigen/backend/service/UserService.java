package com.medigen.backend.service;

import com.medigen.backend.dto.request.LoginRequest;
import com.medigen.backend.dto.request.RegisterRequest;
import com.medigen.backend.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    String login(LoginRequest request);

}