package org.example.service;

import lombok.Data;

@Data
public class ApiResponse {
    private Integer code;
    private String type;
    private String message;
}
