package ru.top.openweathermap21.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Main{
    public double temp;
    @JsonProperty("feels_like")
    public double feelsLike;
    @JsonProperty("temp_min")
    public double tempMin;
    @JsonProperty("temp_max")
    public double tempMax;
    public int pressure;
    public int humidity;
    @JsonProperty("sea_level")
    public int seaLevel;
    @JsonProperty("grnd_level")
    public int grndLevel;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
