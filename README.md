# senla-test-task

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Tomcat 9
- Maven
- PostgreSQL
  
Configure the application properties:
   - Create the `db.properties` file in the `src/main/resources` directory.
   - Set the database connection properties as in db.properties.origin (driver, url, username, password).
   - Run `db.sql` for creation of database with table.

## API Endpoints

The Weather Application provides the following API endpoints:

### 1. Get Weather Information

- **Endpoint:** `GET /weather-info/{from}/{to}`
- **Description:** Retrieves the weather information for the specified date range.
- **Parameters:**
  - `from` (required): The start date (format: `yyyy-MM-dd`).
  - `to` (required): The end date (format: `yyyy-MM-dd`).
- **Response:** An array of weather objects.

### 2. Get Average Temperature

- **Endpoint:** `GET /avg-info/{from}/{to}`
- **Description:** Calculates the average temperature for the specified date range and returns it along with the weather information.
- **Parameters:**
  - `from` (required): The start date (format: `yyyy-MM-dd`).
  - `to` (required): The end date (format: `yyyy-MM-dd`).
- **Response:** An object containing the average temperature (`avgTemperature`) and the weather information (`weatherList`).

## Usage Examples

### Get Weather Information

To retrieve the weather information for a specific date range, send a GET request to the `/weather-info/{from}/{to}` endpoint, replacing `{from}` and `{to}` with the desired dates, for example:

```
GET /weather-info/2023-01-01/2023-01-10
```

The response will include an array of weather objects containing the weather details for each day within the specified date range in JSON format.

### Get Average Temperature

To calculate the average temperature for a specific date range and obtain the weather information, send a GET request to the `/avg-info/{from}/{to}` endpoint, replacing `{from}` and `{to}` with the desired dates, , for example:

```
GET /avg-info/2023-01-01/2023-01-10
```

The response will include an object with the average temperature (`avgTemperature`) and the weather information (`weatherList`) for each day within the specified date range in JSON format.

## Acknowledgments

- [Visual Crossing Weather API](https://rapidapi.com/visual-crossing-corporation-visual-crossing-corporation-default/api/visual-crossing-weather) for providing weather data.
