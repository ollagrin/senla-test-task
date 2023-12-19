package by.hrynko.spring.senlatesttask.dao;

import by.hrynko.spring.senlatesttask.model.Weather;

import java.util.Date;

public interface WeatherDAO {

    boolean isExists(Date date);
    void insertWeather(Weather weather);
    double averageWeather(Date leftBound, Date rightBound);
}
