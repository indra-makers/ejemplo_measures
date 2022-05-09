package com.indramakers.example.measuresms.exeptions;

import com.indramakers.example.measuresms.config.ErrorCodes;

public class BusinessException  extends RuntimeException{
	  private ErrorCodes code;

	    public BusinessException(String message) {
	        super(message);
	    }

	    public BusinessException(ErrorCodes code) {
	        super(code.getMessage());
	        this.code = code;
	    }

	    public String getCode() {
	        return code.getCode();
	    }
}
