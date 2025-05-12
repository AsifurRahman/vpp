package com.asif.vpp.config.authentication.service;

import com.asif.vpp.payload.request.LoginRequest;
import com.asif.vpp.payload.response.JwtResponse;

public interface AuthService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
}
