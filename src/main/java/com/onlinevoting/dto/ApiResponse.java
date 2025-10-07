package com.onlinevoting.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private List<String> errors;
    private String message;

    public ApiResponse(){

    }

    public ApiResponse(boolean success, T data, List<String> errors) {
        this.success = success;
        this.data = data;
        this.errors = errors;
    }
    public ApiResponse(boolean success, T data, List<String> errors, String message) {
        this.success = success;
        this.data = data;
        this.errors = errors;
        this.message = message;
    }

}
