package ru.top.openweathermap21.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigRestTemplate {
    @Bean(name = "restTemplateL")
    public RestTemplate restTemplateL() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
    @Bean(name = "restTemplateS")
    public RestTemplate restTemplateS() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(500);
        factory.setReadTimeout(500);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
}
