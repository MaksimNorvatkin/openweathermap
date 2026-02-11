package ru.top.openweathermap21.utils;

import org.springframework.web.client.RestTemplate;
import ru.top.openweathermap21.model.WeatherApiResponse;

import java.util.LinkedHashMap;
import java.util.Map;

public class RestUtils {
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    String apiKey ="54e888a2765b07ff1b6acffb64223e4b";
    public WeatherApiResponse getWeatherData(String city) {
        String url = baseUrl + "?q="
                + city + "&appid="+apiKey+"&units=metric";

        return restTemplate.getForObject(url, WeatherApiResponse.class);
    }

    public Map<String, Object> convertToMap(WeatherApiResponse response) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("📍 Город", response.getName());
        result.put("🌡️ Температура", String.format("%.1f°C", response.getMain().getTemp()));
        result.put("🤔 Ощущается как", String.format("%.1f°C", response.getMain().getFeelsLike()));
        result.put("💨 Скорость ветра", String.format("%.1f м/с", response.getWind().getSpeed()));
        result.put("💧 Влажность", response.getMain().getHumidity() + "%");
        result.put("☁️ Облачность", response.getClouds().getAll() + "%");
        return result;
    }
}
