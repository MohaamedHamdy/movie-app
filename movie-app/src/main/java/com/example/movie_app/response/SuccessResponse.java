package com.example.movie_app.response;

import lombok.Data;

import java.util.Map;

@Data
public class SuccessResponse {
    private String message;
    private int code;
    private Object data;
}
