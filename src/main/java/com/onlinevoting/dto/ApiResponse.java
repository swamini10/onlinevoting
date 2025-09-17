package com.onlinevoting.dto;

import java.util.List;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private List<String> errors;
    private String message;

    public ApiResponse() {}
    
    public ApiResponse(boolean success, T data, List<String> errors) {
        this.success = success;
        this.data = data;
        this.errors = errors;
    }
    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
