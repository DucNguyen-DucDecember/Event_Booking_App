package com.example.eba.handler;

import com.example.eba.dto.*;
import com.example.eba.exception.BookingNotFoundException;
import com.example.eba.exception.EventNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
            List<JsonMappingException.Reference> path = invalidFormatEx.getPath();
            if (!path.isEmpty()) {
                fieldName = path.get(0).getFieldName();
            }

            message = String.format("Invalid %s format",
                    fieldName,
                    invalidFormatEx.getTargetType().getSimpleName().toLowerCase());
        }

        ErrorDto err = new ErrorDto(fieldName, message);
        ApiResponse<?> body = ApiResponse.error("Invalid request format", List.of(err));
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

}
