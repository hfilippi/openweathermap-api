package ar.com.vampiro.openweathermapapi.model;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ar.com.vampiro.openweathermapapi.error.WeatherApiCallException;

@Component
public class WeatherBasicResponseAdapter {

	public WeahterBasicResponse toWeatherBasicResponse(WeatherResponse response) {
		Optional<Weather> weather = response.getWeather().stream().findFirst();

		if (weather.isPresent()) {
			return WeahterBasicResponse.builder().name(response.getName()).description(weather.get().getDescription())
					.temp(response.getMain().getTemp()).feelsLike(response.getMain().getFeelsLike())
					.icon(weather.get().getIcon()).build();
		}

		throw new WeatherApiCallException();
	}

}
