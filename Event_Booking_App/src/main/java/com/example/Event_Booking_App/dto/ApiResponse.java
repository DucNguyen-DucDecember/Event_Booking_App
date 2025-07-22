package com.example.Event_Booking_App.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ApiResponse<T> {

    private boolean success;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDto> errors;

    public ApiResponse(boolean success, String message, T data, List<ErrorDto> errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }


    public static <T> ApiResponse<T> ok(String msg, T data) {
        return new ApiResponse<>(true, msg, data, null);
    }

    // tiện lợi cho error case
    public static <T> ApiResponse<T> error(String msg, List<ErrorDto> errors) {
        return new ApiResponse<>(false, msg, null, errors);
    }

    // getters / setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public List<ErrorDto> getErrors() { return errors; }
    public void setErrors(List<ErrorDto> errors) { this.errors = errors; }
}
