package ru.top.openweathermap21.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.top.openweathermap21.model.WeatherApiResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    public WeatherController() {

    }
    @GetMapping("/search/{city}")
    public ResponseEntity<WeatherApiResponse> search (@PathVariable String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric";
        ResponseEntity <WeatherApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherApiResponse.class);
//        HttpStatusCode statusCode = response.getStatusCode();
//        if (statusCode.is2xxSuccessful()) {
//            return response;
//        }
        return response;
    }

    @GetMapping("/minimal/{city}")
    public ResponseEntity<Map<String, Object>> getMinimal(@PathVariable String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric&lang=ru";

        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("\uD83D\uDCCD город", response.getName());
        result.put("\uD83C\uDF21\uFE0F температура", response.getMain().getTemp() + "°C");
        result.put("ощущается как", response.getMain().getFeelsLike() + "°C");
        result.put("\uD83D\uDCA8 скорость ветра", response.getWind().getSpeed() + " метров в секунду");
        result.put("облочность", response.getClouds().getAll() + "%");

        return ResponseEntity.ok(result);
    }

}
