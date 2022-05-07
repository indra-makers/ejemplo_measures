package com.indramakers.example.measuresms.exceptions;

import com.indramakers.example.measuresms.config.ErrorCodes;

public class BusinessExceptions extends RuntimeException{
    private ErrorCodes code;

    public BusinessExceptions(String message) {
        super(message);
    }

    public BusinessExceptions(ErrorCodes code) {
        super(code.getMessage());
        this.code = code;
    }

    public String getCode() {
        return code.getCode();
    }
}
