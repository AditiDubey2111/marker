package com.example.marker.dto;

import com.example.marker.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private User.Role role;
    private Long userId;
    private String fullName;
}
