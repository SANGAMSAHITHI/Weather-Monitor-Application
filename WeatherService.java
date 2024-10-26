package com.example.weather_monitoring.service;

import com.example.weather_monitoring.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private static final String[] CITIES = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherData fetchWeatherData() {
        double sumTemp = 0;
        double maxTemp = Double.MIN_VALUE;
        double minTemp = Double.MAX_VALUE;
        String dominantWeather = "";

        for (String city : CITIES) {
            String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
            Map<String, Object> data = restTemplate.getForObject(url, Map.class);
            if (data != null) {
                double temp = kelvinToCelsius((Double) ((Map<String, Object>) data.get("main")).get("temp"));
                sumTemp += temp;
                maxTemp = Math.max(maxTemp, temp);
                minTemp = Math.min(minTemp, temp);
                String weather = (String) ((List<Map<String, String>>) data.get("weather")).get(0).get("main");
                dominantWeather = weather; // Simple implementation, can be refined
            }
        }

        double avgTemp = sumTemp / CITIES.length;

        WeatherData weatherData = new WeatherData();
        weatherData.setDate(LocalDate.now().toString());
        weatherData.setAverageTemp(avgTemp);
        weatherData.setMaxTemp(maxTemp);
        weatherData.setMinTemp(minTemp);
        weatherData.setDominantWeather(dominantWeather);
        return weatherData;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
