package by.hrynko.spring.senlatesttask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    @NotNull
    @UniqueElements
    private Date date;

    @NotNull
    private double temperature;

    @NotNull
    private double windSpeed;

    @NotNull
    private double atmosphericPressure;

    @NotNull
    private double airHumidity;

    @NotNull
    private String weatherCondition;

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+")
    private String location;

    public void setTemperature(double temperature) {
        this.temperature = (temperature - 32) / 1.8;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("date=").append(date);
        sb.append(", temperature=").append(temperature);
        sb.append(", windSpeed=").append(windSpeed);
        sb.append(", atmosphericPressure=").append(atmosphericPressure);
        sb.append(", airHumidity=").append(airHumidity);
        sb.append(", weatherCondition='").append(weatherCondition).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
