package by.hrynko.senlatesttask;

import by.hrynko.spring.senlatesttask.controller.RestWeatherController;
import by.hrynko.spring.senlatesttask.dao.WeatherDAO;
import by.hrynko.spring.senlatesttask.model.Weather;
import by.hrynko.spring.senlatesttask.weatherApi.WeatherApiConnector;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherControllerTest {
    @Mock
    private WeatherApiConnector weatherApiConnector;

    @Mock
    private WeatherDAO weatherDAO;

    @InjectMocks
    private RestWeatherController weatherController;

    public WeatherControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherInfo() throws IOException, ParseException {
        // Arrange
        Map<String, String> pathVarsMap = new HashMap<>();
        pathVarsMap.put("from", "2023-01-01");
        pathVarsMap.put("to", "2023-01-02");
        Date date1 = java.sql.Date.valueOf("2023-01-01");
        Date date2 = java.sql.Date.valueOf("2023-01-02");
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(new Weather(date1, -4.388889, 8.9, 1002.5, 96.16, "Overcast", "Minsk, Belarus"));
        weatherList.add(new Weather(date2, -5.3333335, 8.9, 1006.8, 96.61, "Overcast", "Minsk, Belarus"));
        when(weatherApiConnector.getWeather(date1, date2)).thenReturn(weatherList);

        // Act
        List<Weather> result = weatherController.getWeatherInfo(pathVarsMap);

        // Assert
        assertEquals(weatherList, result);
    }

    @Test
    public void testGetAverage() throws IOException, ParseException {
        // Arrange
        Map<String, String> pathVarsMap = new HashMap<>();
        pathVarsMap.put("from", "2023-01-01");
        pathVarsMap.put("to", "2023-01-02");
        Date date1 = java.sql.Date.valueOf("2023-01-01");
        Date date2 = java.sql.Date.valueOf("2023-01-02");
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(new Weather(date1, -4.388889, 8.9, 1002.5, 96.16, "Overcast", "Minsk, Belarus"));
        weatherList.add(new Weather(date2, -5.3333335, 8.9, 1006.8, 96.61, "Overcast", "Minsk, Belarus"));
        when(weatherApiConnector.getWeather(date1, date2)).thenReturn(weatherList);
        when(weatherDAO.averageWeather(date1, date2)).thenReturn(-4.86111125);

        // Act
        Map<String, Object> result = weatherController.getAverage(pathVarsMap);

        // Assert
        assertEquals(-4.86111125, result.get("avgTemperature"));
        assertEquals(weatherList, result.get("weatherList"));
    }
}
