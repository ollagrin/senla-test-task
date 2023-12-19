package by.hrynko.spring.senlatesttask.weatherApi;

import by.hrynko.spring.senlatesttask.model.Weather;

import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherApiConnector {
    private final static String RAPID_KEY = "cc89aa3485msh490a8aadf8a9dbap1a3959jsn9d1be64e3829";
    private final static String RAPID_HOST = "visual-crossing-weather.p.rapidapi.com";
    public final static String CITY = "Minsk, Belarus";

    public List<Weather> getWeather(Date leftBound, Date rightBound) throws IOException, ParseException {
        String dataJson = getWeatherJSON(leftBound, rightBound);
        JSONArray jsonArray = new
                JSONObject(dataJson).
                getJSONObject("locations").getJSONObject(CITY).
                getJSONArray("values");

        List<Weather> weatherList = new ArrayList<>();
        Date date;
        double temperature;
        double windSpeed;
        double atmosphericPressure;
        double airHumidity;
        String weatherCondition;

        JSONObject tempObj;
        Weather weather;

        for (int i = 0; i < jsonArray.length(); ++i) {
            tempObj = jsonArray.
                    getJSONObject(i);
            String tempDate = tempObj.
                    getString("datetimeStr");
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(tempDate);
            LocalDate localDate = zonedDateTime.toLocalDate();
            date = java.sql.Date.valueOf(localDate);

            temperature = tempObj.
                    getDouble("temp");
            windSpeed = tempObj.
                    getDouble("wspd");
            atmosphericPressure = tempObj.
                    getDouble("sealevelpressure");
            airHumidity = tempObj.
                    getDouble("humidity");
            weatherCondition = jsonArray.
                    getJSONObject(i).
                    getString("conditions");

            weather = new Weather();
            weather.setDate(date);
            weather.setTemperature(temperature);
            weather.setWindSpeed(windSpeed);
            weather.setAtmosphericPressure(atmosphericPressure);
            weather.setAirHumidity(airHumidity);
            weather.setWeatherCondition(weatherCondition);
            weather.setLocation(CITY);

            weatherList.add(weather);
        }

        return weatherList;
    }

    private String getWeatherJSON(Date leftBound, Date rightBound) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://visual-crossing-weather.p.rapidapi.com/history?startDateTime=" +
                        leftBound +
                        "&aggregateHours=24&location=" +
                        CITY +
                        "&endDateTime=" +
                        rightBound +
                        "&unitGroup=us&dayStartTime=0%3A00%3A00&contentType=json&dayEndTime=23%3A59%3A59&shortColumnNames=0")
                .get()
                .addHeader("X-RapidAPI-Key", RAPID_KEY)
                .addHeader("X-RapidAPI-Host", RAPID_HOST)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}