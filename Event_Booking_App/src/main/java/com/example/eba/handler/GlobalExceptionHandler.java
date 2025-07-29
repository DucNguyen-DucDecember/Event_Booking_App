package com.example.eba.handler;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.ErrorDto;
import com.example.eba.exception.BookingNotFoundException;
import com.example.eba.exception.EventNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ErrorDto(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponse.error("Validation failed", errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Invalid email or password", null, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleParseError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        String fieldName = "unknown";
        String message = "Validation failed";

        if (cause instanceof InvalidFormatException invalidFormatEx) {
            List<com.fasterxml.jackson.databind.JsonMappingException.Reference> path = invalidFormatEx.getPath();
            if (!path.isEmpty()) {
                fieldName = path.get(0).getFieldName();
            }
            if ("eventReminder".equals(fieldName)) {
                message = "Must be true or false";
            } else {
                message = String.format("Invalid %s format", fieldName);
            }
        } else if (ex.getMessage().contains("Required request body is missing")) {
            fieldName = "eventReminder";
            message = "Must be true or false";
        }

        ErrorDto err = new ErrorDto(fieldName, message);
        ApiResponse<?> body = ApiResponse.error("Validation failed", List.of(err));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Void>> handleEventNotFound(EventNotFoundException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Void>> handleBookingNotFound(BookingNotFoundException ex) {
        ErrorDto err = new ErrorDto("bookingId", "Booking not found or already paid");
        List<ErrorDto> errors = List.of(err);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.error("Validation failed", errors));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        ErrorDto error = new ErrorDto(ex.getParameterName(), "Required parameter is missing");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid parameter", List.of(error)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDto error = new ErrorDto("invalid_param", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponse.error("Validation failed", List.of(error)));
    }
}