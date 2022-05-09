package com.indramakers.example.measuresms.exceptions;

import com.indramakers.example.measuresms.config.ErrorCodes;

public class NotFoundException extends RuntimeException{
    public NotFoundException(ErrorCodes message) {
        super(message.getMessage());
    }
}