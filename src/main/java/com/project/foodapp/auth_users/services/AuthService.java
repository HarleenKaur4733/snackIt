package com.project.foodapp.auth_users.services;

import com.project.foodapp.auth_users.dtos.LoginRequest;
import com.project.foodapp.auth_users.dtos.LoginResponse;
import com.project.foodapp.auth_users.dtos.RegistrationRequest;
import com.project.foodapp.response.Response;

public interface AuthService {
    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
