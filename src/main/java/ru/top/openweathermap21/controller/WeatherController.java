package ru.top.openweathermap21.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.top.openweathermap21.model.SimpleCache;
import ru.top.openweathermap21.model.WeatherApiResponse;
import ru.top.openweathermap21.services.WeatherApiServices;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    WeatherApiServices weatherApiServices = new WeatherApiServices();

    @GetMapping("/search/{city}")
    public ResponseEntity<?> search (@PathVariable String city) {
        return weatherApiServices.search(city);
    }

    @GetMapping("/minimal/{city}")
    public ResponseEntity<?> getMinimal(@PathVariable String city) {
        return weatherApiServices.getMinimal(city);
    }

}
