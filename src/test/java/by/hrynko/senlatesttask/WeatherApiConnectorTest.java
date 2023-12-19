package by.hrynko.senlatesttask;

import by.hrynko.spring.senlatesttask.model.Weather;
import by.hrynko.spring.senlatesttask.weatherApi.WeatherApiConnector;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.util.List;

public class WeatherApiConnectorTest {
    @Test
    public void testEndpoint(){
        WeatherApiConnector weatherApiConnector = new WeatherApiConnector();
        try {
            Date date1 = java.sql.Date.valueOf("2018-10-18");
            Date date2 = java.sql.Date.valueOf("2018-10-20");
            List<Weather> weatherList = weatherApiConnector.getWeather(date1, date2);
            for(Weather w : weatherList){
                System.out.println(w);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
