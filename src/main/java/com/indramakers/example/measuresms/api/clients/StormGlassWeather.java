package com.indramakers.example.measuresms.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class StormGlassWeather {
    public static class AirTemperature {
        private String dwd;

        public AirTemperature() {
        }

        public AirTemperature(String dwd) {
            this.dwd = dwd;
        }

        public String getDwd() {
            return dwd;
        }

        public void setDwd(String dwd) {
            this.dwd = dwd;
        }
    }
    public static class Hours {
        private String time;
        private AirTemperature airTemperature;

        public Hours(String time, AirTemperature airTemperature) {
            this.time = time;
            this.airTemperature = airTemperature;
        }

        public Hours() {
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public AirTemperature getAirTemperature() {
            return airTemperature;
        }

        public void setAirTemperature(AirTemperature airTemperature) {
            this.airTemperature = airTemperature;
        }
    }

    @JsonProperty("hours")
    private List<Hours> data;

    public StormGlassWeather() {
    }

    public StormGlassWeather(List<Hours> data) {
        this.data = data;
    }

    public List<Hours> getData() {
        return data;
    }

    public void setData(List<Hours> data) {
        this.data = data;
    }
}
