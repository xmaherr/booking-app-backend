package com.maher.bookingapp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            RoomNotFoundException.class,
            UserNotFoundException.class,
            BookingNotFoundException.class,
            EmailAlreadyExistsException.class,
            WrongEmailOrPassword.class
    })
    public ResponseEntity<Object> handleCustomException(RuntimeException ex) {
        HttpStatus status = determineHttpStatus(ex);
        return buildErrorResponse(ex.getMessage(), status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing)
                );

        return ResponseEntity.badRequest().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "errors", validationErrors
                )
        );
    }


    private HttpStatus determineHttpStatus(RuntimeException ex) {
        if (ex instanceof RoomNotFoundException || ex instanceof UserNotFoundException || ex instanceof BookingNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof EmailAlreadyExistsException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof WrongEmailOrPassword) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // ✅ إنشاء استجابة خطأ موحدة
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", message,
                        "status", status.value()
                )
        );
    }
}
