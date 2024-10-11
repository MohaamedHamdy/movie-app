package com.example.movie_app.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String code;
}
