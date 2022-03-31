package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberModel {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean emailSent;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
