package com.earuile.bubble.mp.rest.handler;

import com.earuile.bubble.mp.core.service.user.exception.CannotRegisterUser;
import com.earuile.bubble.mp.db.exception.ChatNotFound;
import com.earuile.bubble.mp.db.exception.UserNotFound;
import com.earuile.bubble.mp.rest.exception.ValidationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<String> handleMessageUnsupportedMediaType(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(CannotRegisterUser.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleMessageUnprocessableEntity(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ResponseEntity<String> handleNullPointer(RuntimeException e) {
        return new ResponseEntity<>("Это NullPointer на серваке, пуся! " + e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

}