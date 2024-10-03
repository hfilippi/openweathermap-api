package ar.com.vampiro.openweathermapapi.service;

import java.util.Optional;

import ar.com.vampiro.openweathermapapi.model.TempResponse;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {

	Mono<WeatherResponse> weather(Double latitude, Double longitude, Optional<String> units, Optional<String> lang,
			Optional<String> timezone);

	TempResponse temp(Optional<Double> latitude, Optional<Double> longitude);

}
