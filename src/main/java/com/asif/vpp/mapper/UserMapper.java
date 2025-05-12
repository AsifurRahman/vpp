package com.asif.vpp.mapper;

import com.asif.vpp.model.User;
import com.asif.vpp.payload.request.UserRequestDto;
import com.asif.vpp.payload.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto convertToResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .createdAt(user.getCreatedTime())
                .updatedAt(user.getUpdatedTime())
                .createdById(user.getCreatedBy().getId())
                .updatedById(Objects.nonNull(user.getUpdatedBy())? user.getUpdatedBy().getId() : null)
                .isActive(user.getIsActive())
                .build();
    }

    public User convertToEntity(UserRequestDto dto) {
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setUsername(dto.getUsername());
        return entity;
    }
}
