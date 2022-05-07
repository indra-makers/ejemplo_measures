package com.indramakers.example.measuresms.config;

public class Routes {
    public static final String DEVICES_PATH = "/devices";
    public static final String DEVICE_BY_BRANCH_PATH = "/by-branch";
    public static final String MEASURES_BY_DEVICE_PATH = "/{deviceId}/measures";
    public static final String DEVICES_BY_LOCATION_ID_PATH = "/getbyLocation";
    public static final String LOCATIONS_PATH = "/locations";
    public static final String LOCATIONS_BY_ID = "/by_id";
    public static final String DELETE_LOCATION_BY_ID = "/delete/{id_location}";
    public static final String MEASURES_VALUE_BY_LOCATION = "/{id_location}/measures_value";
    public static final String MEASURES_BY_LOCATION = "/{id_location}/measures";


}
