package ar.com.vampiro.openweathermapapi.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import ar.com.vampiro.openweathermapapi.service.WeatherService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/openweather/v1/weather")
@CrossOrigin
public class WeatherController {

	private WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping
	public Mono<WeatherResponse> weather(@RequestParam Double latitude, @RequestParam Double longitude,
			@RequestParam Optional<String> units, @RequestParam Optional<String> lang,
			@RequestParam Optional<String> timezone) {
		return this.weatherService.weather(latitude, longitude, units, lang, timezone);
	}

}
