package ru.top.openweathermap21.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ru.top.openweathermap21.model.WeatherApiResponse;
import ru.top.openweathermap21.utils.RestUtils;
import ru.top.openweathermap21.utils.SimpleCache;


public class WeatherApiServices {

    SimpleCache cache = new SimpleCache(60 * 1000);
    RestUtils restUtils = new RestUtils();

    public ResponseEntity<?> search(String city) {
        city = city.toLowerCase();

        try {
            WeatherApiResponse cachedResponse = cache.getWeather(city);
            if (cachedResponse != null) {
                return ResponseEntity.ok(cachedResponse);
            }

            WeatherApiResponse response = restUtils.getWeatherData(city);

            if (response == null) {
                return ResponseEntity.status(500).body("Ошибка: пустой ответ от сервера");
            }

            cache.putWeather(city, response);
            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Город '" + city + "' не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getMinimal(String city) {
        city = city.toLowerCase();

        try {
            WeatherApiResponse cachedResponse = cache.getWeather(city);
            if (cachedResponse != null) {
                return ResponseEntity.ok(restUtils.convertToMap(cachedResponse));
            }

            WeatherApiResponse response = restUtils.getWeatherData(city);

            if (response == null) {
                return ResponseEntity.status(500).body("Ошибка: пустой ответ от сервера");
            }

            cache.putWeather(city, response);
            return ResponseEntity.ok(restUtils.convertToMap(response));

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Город '" + city + "' не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}