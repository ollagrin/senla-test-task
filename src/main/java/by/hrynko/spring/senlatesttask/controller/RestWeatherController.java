package by.hrynko.spring.senlatesttask.controller;

import by.hrynko.spring.senlatesttask.dao.WeatherDAO;
import by.hrynko.spring.senlatesttask.model.Weather;
import by.hrynko.spring.senlatesttask.weatherApi.WeatherApiConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RestWeatherController {
    private WeatherApiConnector weatherApiConnector;
    private WeatherDAO weatherDAO;

    @Autowired
    public RestWeatherController(WeatherApiConnector weatherApiConnector, WeatherDAO weatherDAO) {
        this.weatherApiConnector = weatherApiConnector;
        this.weatherDAO = weatherDAO;
    }

    @GetMapping("")
    public String index(){
        return "Weather App";
    }

    //Endpoint 1
    @GetMapping("/weather-info/{from}/{to}")
    public List<Weather> getWeatherInfo(@PathVariable Map<String, String> pathVarsMap) throws IOException, ParseException {
        Date date1 = java.sql.Date.valueOf(pathVarsMap.get("from"));
        Date date2 = java.sql.Date.valueOf(pathVarsMap.get("to"));
        List<Weather> weatherList = weatherApiConnector.getWeather(date1, date2);
        for (Weather w : weatherList) {
            if (!weatherDAO.isExists(w.getDate())) {
                weatherDAO.insertWeather(w);
            }
        }
        return weatherList;
    }

    //Endpoint 2
    @GetMapping("/avg-info/{from}/{to}")
    public Map<String, Object> getAverage(@PathVariable Map<String, String> pathVarsMap) {
        double avgTemperature = 0;
        List<Weather> weatherList = null;
        try {
            Date date1 = java.sql.Date.valueOf(pathVarsMap.get("from"));
            Date date2 = java.sql.Date.valueOf(pathVarsMap.get("to"));
            weatherList = weatherApiConnector.getWeather(date1, date2);
            for (Weather w : weatherList) {
                if (!weatherDAO.isExists(w.getDate())) {
                    weatherDAO.insertWeather(w);
                }
            }
            avgTemperature = weatherDAO.averageWeather(date1, date2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("avgTemperature", avgTemperature);
        response.put("weatherList", weatherList);
        return response;
    }
}
