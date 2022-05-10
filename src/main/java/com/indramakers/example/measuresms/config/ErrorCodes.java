package com.indramakers.example.measuresms.config;

public enum ErrorCodes {
	DEVICE_WITH_NAME_EXISTS("Device with that name already exists", "001"),
	MEASURE_VALUES_OUT_OF_RANGE("Measures must be in range", "002"), 
	DEVICE_NOT_FOUND("Device not found", "0002"),
	LOCATION_DOESNT_EXIST("This Location not exist", "003"),
	DEVICE_ALREADY_USE_LOCATION("This Location already use device", "004"),
	LOCATION_NOT_DELETE("This Location can not delete", "005");

	String message;
	String code;

	ErrorCodes(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

}
