package ar.com.vampiro.openweathermapapi.service;

import java.util.Optional;

import ar.com.vampiro.openweathermapapi.model.WeahterBasicResponse;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;

public interface WeatherService {

	WeatherResponse weather(Double lat, Double lon, Optional<String> units, Optional<String> lang,
			Optional<String> timezone);

	WeahterBasicResponse weatherBasic(Double lat, Double lon, Optional<String> units, Optional<String> lang,
			Optional<String> timezone);

	String weatherPicoDisplay(Double lat, Double lon, Optional<String> units, Optional<String> lang,
			Optional<String> timezone);

}
