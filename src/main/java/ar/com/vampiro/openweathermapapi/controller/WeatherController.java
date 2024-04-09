package ar.com.vampiro.openweathermapapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.vampiro.openweathermapapi.model.WeahterBasicResponse;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import ar.com.vampiro.openweathermapapi.service.WeatherService;

@RestController
@RequestMapping(value = "/openweather/v1/weather")
@CrossOrigin
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping
	public WeatherResponse weather(@RequestParam Double lat, @RequestParam Double lon,
			@RequestParam Optional<String> units, @RequestParam Optional<String> lang,
			@RequestParam Optional<String> timezone) {
		return this.weatherService.weather(lat, lon, units, lang, timezone);
	}

	@GetMapping("basic")
	public WeahterBasicResponse weatherBasic(@RequestParam Double lat, @RequestParam Double lon,
			@RequestParam Optional<String> units, @RequestParam Optional<String> lang,
			@RequestParam Optional<String> timezone) {
		return this.weatherService.weatherBasic(lat, lon, units, lang, timezone);
	}

	@GetMapping("pico_display")
	public String weatherPicoDisplay(@RequestParam Double lat, @RequestParam Double lon,
			@RequestParam Optional<String> units, @RequestParam Optional<String> lang,
			@RequestParam Optional<String> timezone) {
		return this.weatherService.weatherPicoDisplay(lat, lon, units, lang, timezone);
	}

}
