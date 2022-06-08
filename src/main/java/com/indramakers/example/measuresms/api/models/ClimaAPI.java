package com.indramakers.example.measuresms.api.models;

import java.io.Serializable;

public class ClimaAPI implements Serializable {

    private Double temperatura;
    private Double humedad;
    private Double presion;

    public ClimaAPI() {
    }

    public ClimaAPI(Double temperatura, Double humedad, Double presion) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.presion = presion;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getHumedad() {
        return humedad;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public Double getPresion() {
        return presion;
    }

    public void setPresion(Double presion) {
        this.presion = presion;
    }
}
