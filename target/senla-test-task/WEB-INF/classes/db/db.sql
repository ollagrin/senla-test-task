create database senlaTask
    with
    owner = postgres;

create table weatherByDay
(
    weatherId           serial primary key,
    "date"              date        not null unique,
    temperature         real        not null,
    windSpeed           real        not null,
    atmosphericPressure real        not null,
    airHumidity         real        not null,
    weatherCondition    varchar(50) not null,
    location            varchar(50) not null
);