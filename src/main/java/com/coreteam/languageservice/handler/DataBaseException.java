package com.coreteam.languageservice.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataBaseException extends RuntimeException{
    private final Integer code;
    public DataBaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
