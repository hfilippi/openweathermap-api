package ar.com.vampiro.openweathermapapi.service;

import java.util.Optional;

import ar.com.vampiro.openweathermapapi.model.WeatherResponse;

public interface WeatherService {

	WeatherResponse weather(Double latitude, Double longitude, Optional<String> units, Optional<String> lang,
			Optional<String> timezone);

}
