package ru.top.openweathermap21.utils;

import ru.top.openweathermap21.model.WeatherApiResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache {
    Map<String , CacheData> cache =  new ConcurrentHashMap<>();
    private  long timeToLive;
    public SimpleCache(long timeToLive) {
        this.timeToLive = timeToLive;
    }
    public void putWeather(String key, WeatherApiResponse data) {
        cache.put(key, new CacheData(data));
    }
    public WeatherApiResponse getWeather(String key) {
        CacheData cacheData = cache.get(key);
        if (cacheData == null)
            return null;
        if (cacheData.isExpired()) {
            cache.remove(key);
            return  null;
        }
        return cacheData.data;
    }
    public int size() {
        return cache.size();
    }
    public  void clear() {
        cache.clear();
    }

    public class CacheData{
        long time;
        WeatherApiResponse data;

        public CacheData(WeatherApiResponse data) {
            this.data = data;
            this.time = System.currentTimeMillis();
        }

        public boolean isExpired(){
            return (System.currentTimeMillis() - this.time) > timeToLive;
        }
    }
}
