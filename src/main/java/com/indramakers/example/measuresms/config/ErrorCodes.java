package com.indramakers.example.measuresms.config;

public enum ErrorCodes {
    DEVICE_WITH_NAME_EXISTS("Device with that name already exists", "001"),
    MEASURE_VALUES_OUT_OF_RANGE("Measures must be in range", "002"),
    DEVICE_NOT_FOUND("Device not found", "003"),
    INVALID_ID_BRANCH("Invalid branch", "004"),
    INVALID_ID_LOCATION("Invalid IdLocation", "005"),

    ID_LOCATION_NOT_EXIST("IdLocation not exist", "006"),
    INVALID_ID_DEVICE("Invalid IdDevice", "007");

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
