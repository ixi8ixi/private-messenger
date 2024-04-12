package com.earuile.bubble.mp.rest.handler;

import com.earuile.bubble.mp.core.service.user.exception.CannotRegisterUser;
import com.earuile.bubble.mp.db.exception.ChatNotFound;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.rest.exception.ValidationException;
import com.earuile.bubble.mp.rest.handler.info.StackTraceResponse;
import com.earuile.bubble.mp.rest.handler.info.ValidationErrorResponse;
import com.earuile.bubble.mp.rest.handler.info.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConversion(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ChatNotFound.class, UserNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleMessageNotFound(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            ValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ValidationErrorResponse handleMessageUnsupportedMediaType(Exception e) {
        return switch (e) {
            case ValidationException validationException -> ValidationErrorResponse.builder()
                    .violations(List.of(Violation.builder()
                            .message(validationException.getMessage())
                            .build()))
                    .build();
            case MethodArgumentNotValidException methodArgumentNotValidException -> ValidationErrorResponse.builder()
                    .violations(methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                            .map(error -> Violation.builder()
                                    .fieldName(error.getField())
                                    .message(error.getDefaultMessage())
                                    .build())
                            .toList())
                    .build();
            case ConstraintViolationException constraintViolationException -> ValidationErrorResponse.builder()
                    .violations(constraintViolationException.getConstraintViolations()
                            .stream()
                            .map(constraintViolation -> Violation.builder()
                                    .fieldName(constraintViolation.getConstraintDescriptor().getValidationAppliesTo().name())
                                    .message(constraintViolation.getMessage())
                                    .build())
                            .toList())
                    .build();
            default -> throw new RuntimeException("Server error in error handling");
        };
    }

    @ExceptionHandler(CannotRegisterUser.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleMessageUnprocessableEntity(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public StackTraceResponse handleNullPointer(RuntimeException e) {
        return new StackTraceResponse(e, "Это NullPointer на серваке, пуся!");
    }

}