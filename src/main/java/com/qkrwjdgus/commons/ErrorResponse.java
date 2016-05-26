package com.qkrwjdgus.commons;

import lombok.Data;

import java.util.List;

/**
 * @author PJH
 * @since 2016-05-26.
 */

@Data
public class ErrorResponse {

    private String message;

    private String code;

    private List<FieldError> errors;

    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}
