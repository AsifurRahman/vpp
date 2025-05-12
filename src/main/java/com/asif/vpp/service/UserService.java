package com.asif.vpp.service;

import com.asif.vpp.model.User;
import com.asif.vpp.payload.request.UserRequestDto;
import com.asif.vpp.payload.response.UserResponseDto;

import java.util.Set;

public interface UserService {
    User findByUserName(String username);

    User findByUserId(Long userId);
    UserResponseDto createUser(UserRequestDto requestDto);
    User save(UserRequestDto requestDto);

    Set<User> findAllUserByIdIn(Set<Long> userIds, Boolean isActive);
}
