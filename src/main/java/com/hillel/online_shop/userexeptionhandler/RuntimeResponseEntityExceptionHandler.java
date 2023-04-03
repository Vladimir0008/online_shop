package com.hillel.online_shop.userexeptionhandler;

import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RuntimeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ProductNotFoundException.class, UserNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception e, WebRequest request) {
        String bodyOfResponse = e.getMessage();

        return handleExceptionInternal(
                e,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }
}
