package com.example.weather_monitoring.controller;

import com.example.weather_monitoring.model.WeatherData;
import com.example.weather_monitoring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/update")
    public WeatherData updateWeather() {
        return weatherService.fetchWeatherData(); // Fetches and returns the latest weather data
    }

    @GetMapping("/summaries")
    public List<WeatherData> getSummaries() {
        return weatherService.getSummaries(); // Implement this method in service to retrieve summaries
    }

}
