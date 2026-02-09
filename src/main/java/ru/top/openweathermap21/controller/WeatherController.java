package ru.top.openweathermap21.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
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
    public ResponseEntity<?> search (@PathVariable String city) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric";
                ResponseEntity <WeatherApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherApiResponse.class);
                return ResponseEntity.ok(response);

            } catch (HttpClientErrorException.NotFound e) {
                return ResponseEntity.status(404).body("Город '" + city + "' не найден");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
            }
        }

    @GetMapping("/minimal/{city}")
    public ResponseEntity<?> getMinimal(@PathVariable String city) {
                try {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric&lang=ru";

        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("\uD83D\uDCCD Город", response.getName());
        result.put("\uD83C\uDF21\uFE0F Температура", response.getMain().getTemp() + "°C");
        result.put("Ощущается как", response.getMain().getFeelsLike() + "°C");
        result.put("\uD83D\uDCA8 Скорость ветра", response.getWind().getSpeed() + " метров в секунду");
        result.put("Влажность",response.getMain().getHumidity() + "%");
        result.put("Облачность", response.getClouds().getAll() + "%");

        return ResponseEntity.ok(result);

    } catch (HttpClientErrorException.NotFound e) {
        return ResponseEntity.status(404).body("Город '" + city + "' не найден");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
    }
}
}
