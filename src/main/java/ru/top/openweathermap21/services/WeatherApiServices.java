package ru.top.openweathermap21.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.top.openweathermap21.model.SimpleCache;
import ru.top.openweathermap21.model.WeatherApiResponse;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherApiServices {

    SimpleCache cache = new SimpleCache(60 * 1000);
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/search/{city}")
    public ResponseEntity<?> search (String city) {
        city = city.toLowerCase();
        WeatherApiResponse cachedResponse = cache.getWeather(city);
        if (cachedResponse != null) {
            return ResponseEntity.ok(cachedResponse);
        }
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric";
            ResponseEntity <WeatherApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherApiResponse.class);
            cache.putWeather(city, response.getBody());
            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Город '" + city + "' не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/minimal/{city}")
    public ResponseEntity<?> getMinimal(String city) {
        city = city.toLowerCase();
        WeatherApiResponse cachedResponse = cache.getWeather(city);
        if (cachedResponse != null) {
            Map<String, Object> result = convertToMap(cachedResponse);
            return ResponseEntity.ok(result);
        }

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=54e888a2765b07ff1b6acffb64223e4b&units=metric&lang=ru";

            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

            if (response == null) {
                return ResponseEntity.status(500).body("Ошибка: пустой ответ от сервера");
            }

            cache.putWeather(city, response);

            Map<String, Object> result = convertToMap(response);
            return ResponseEntity.ok(result);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Город '" + city + "' не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    private Map<String, Object> convertToMap(WeatherApiResponse response) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("📍 Город", response.getName());
        result.put("🌡️ Температура", String.format("%.1f°C", response.getMain().getTemp()));
        result.put("🤔 Ощущается как", String.format("%.1f°C", response.getMain().getFeelsLike()));
        result.put("💨 Скорость ветра", String.format("%.1f м/с", response.getWind().getSpeed()));
        result.put("💧 Влажность", response.getMain().getHumidity() + "%");
        result.put("\uD83C\uDF25\uFE0F️ Облачность", response.getClouds().getAll() + "%");

        return result;
    }
}
