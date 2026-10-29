package com.heima.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private String role;
    private String massage;
    private LocalDateTime data;
}
