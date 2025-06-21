package com.amachi.app.vitalia.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private int statusCode;
//    private final HttpStatus status;
    private Date timestamp;
    private String message;
    private String description;
    private String origin;
}
