package ru.top.openweathermap21.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.openweathermap21.services.WeatherApiServices;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    WeatherApiServices weatherApiServices;
    @Autowired
    public WeatherController(WeatherApiServices weatherApiServices) {
        this.weatherApiServices = weatherApiServices;
    }

    @GetMapping("/search/{city}")
    public ResponseEntity<?> search (@PathVariable String city) {
        return weatherApiServices.search(city);
    }

    @GetMapping("/minimal/{city}")
    public ResponseEntity<?> getMinimal(@PathVariable String city) {
        return weatherApiServices.getMinimal(city);
    }

}
