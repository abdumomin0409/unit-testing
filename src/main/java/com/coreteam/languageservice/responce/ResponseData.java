package com.coreteam.languageservice.responce;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public final class ResponseData<D> implements Serializable {

    private Boolean success;
    private HttpStatus httpStatus;
    private Integer code;
    private String message;
    private D data;
    private String path;
    private ZonedDateTime date = ZonedDateTime.now();

    public ResponseData(Boolean success, HttpStatus httpStatus) {
        this.success = success;
        this.httpStatus = httpStatus;
    }
}