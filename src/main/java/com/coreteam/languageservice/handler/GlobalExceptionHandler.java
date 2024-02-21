package com.coreteam.languageservice.handler;


import com.coreteam.languageservice.responce.ResponseData;
import io.micrometer.core.instrument.config.validate.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.webjars.NotFoundException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseData<?>> notFoundException(NotFoundException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({DataBaseException.class})
    public ResponseEntity<ResponseData<?>> notFoundException(DataBaseException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).code(ex.getCode()).httpStatus(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ResponseData<?>> notFoundException(NullPointerException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.badRequest().body(path);
    }


    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ResponseData<?>> validationException(ValidationException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({Error.class})
    public ResponseEntity<ResponseData<?>> userAlreadyTakenException(Error ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseData<?>> userNotFoundException(RuntimeException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<ResponseData<?>> userNotFoundException(DataAccessException ex, WebRequest request) {
        ResponseData<Object> path = ResponseData.builder().success(false).message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.badRequest().body(path);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseData<?>> userNotFoundException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        ResponseData<Object> path = ResponseData.builder().success(false).data(errorBody)
                .httpStatus(HttpStatus.NOT_ACCEPTABLE).build();
        return ResponseEntity.badRequest().body(path);
    }


}
