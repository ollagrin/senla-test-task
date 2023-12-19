package by.hrynko.spring.senlatesttask.dao;

import by.hrynko.spring.senlatesttask.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WeatherDAOImpl implements WeatherDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WeatherDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isExists(Date date) {
        Weather weather = jdbcTemplate.query("select * from weatherByDay where \"date\" = ?",
                new BeanPropertyRowMapper<>(Weather.class), date
        ).stream().findAny().orElse(null);

        return weather != null;
    }

    @Override
    public void insertWeather(Weather weather) {
        jdbcTemplate.update
                ("insert into weatherByDay(\"date\", temperature, windSpeed, atmosphericPressure, airHumidity, weatherCondition, location) " +
                                "values (?,?,?,?,?,?,?)",
                        weather.getDate(),
                        weather.getTemperature(),
                        weather.getWindSpeed(),
                        weather.getAtmosphericPressure(),
                        weather.getAirHumidity(),
                        weather.getWeatherCondition(),
                        weather.getLocation()
                );
    }

    @Override
    public double averageWeather(Date leftBound, Date rightBound) {
        return jdbcTemplate.
                queryForList("select AVG(temperature) from weatherByDay where \"date\" between ? and ?",
                        Double.class,
                        leftBound,
                        rightBound).get(0);
    }
}
