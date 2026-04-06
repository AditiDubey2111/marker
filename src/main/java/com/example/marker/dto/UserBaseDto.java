package com.example.marker.dto;

import com.example.marker.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBaseDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private User.Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
