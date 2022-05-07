package com.indramakers.example.measuresms.config;

public enum ErrorCodes {

    DEVICE_WITH_NAME_EXISTS("Device with that name already exists", "001"),
    MEASURE_VALUES_OUT_OF_RANGE("Measures must be in range", "002"),
    NO_LOCATIONS_FOUND("No locations found, please create 1", "003"),
    LOCATION_INVALID("The location with given ID does not exists", "004"),
    LOCATION_WITH_NAME_EXISTS("Error, current name location in use.", "006"),
    DEVICE_REFERENCED_TO_LOCATION("There is a device referenced with current location, first delete the device.", "007"),
    DEVICE_DOES_NOT_EXISTS("Current ID device does not exists.", "009"),
    NO_MEASURES_FOUND("There is not measures, please register one.", "010");






    String message;
    String code;

    ErrorCodes(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
