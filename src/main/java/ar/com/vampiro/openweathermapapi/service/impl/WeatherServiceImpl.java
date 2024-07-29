package ar.com.vampiro.openweathermapapi.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ar.com.vampiro.openweathermapapi.error.RestTemplateResponseErrorHandler;
import ar.com.vampiro.openweathermapapi.error.WeatherApiCallException;
import ar.com.vampiro.openweathermapapi.model.Weather;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import ar.com.vampiro.openweathermapapi.service.WeatherService;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenWeather API consumer SpringBoot microservice.
 * 
 * @see <link>https://openweathermap.org/current#data</link>
 * @author hfilippi
 */

@Slf4j
@Service
@CacheConfig(cacheNames = { "weather-cache" })
public class WeatherServiceImpl implements WeatherService {

	private RestTemplate restTemplate;

	@Value("${openweathermap-org.url}")
	private String url;

	@Value("${openweathermap-org.api_key}")
	private String apiKey;

	@Value("${openweathermap-org.units:metric}")
	private String defaultUnits;

	@Value("${openweathermap-org.lang}")
	private String defaultLang;

	@Value("${openweathermap-org.timezone}")
	private String defaultTimezone;

	@Value("${openweathermap-org.icon_url}")
	private String iconUrlTemplate;

	@Autowired
	public WeatherServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
	}

	@Override
	@Cacheable(value = "weather-cache")
	public WeatherResponse weather(Double latitude, Double longitude, Optional<String> units, Optional<String> lang,
			Optional<String> timezone) {
		// Build API URI
		String uri = this.buildApiUri(String.valueOf(latitude), String.valueOf(longitude),
				units.orElse(this.defaultUnits), lang.orElse(this.defaultLang));
		log.info("*** API URI: {}", this.maskQueryParam(uri, "appid"));

		// API call
		WeatherResponse response = this.restTemplate.getForObject(uri, WeatherResponse.class);

		if (response == null) {
			throw new WeatherApiCallException();
		}

		// Set sunrise_time and sunset_time
		response.getSys().setSunriseTime(
				this.localDateTime(response.getSys().getSunrise(), timezone.orElse(this.defaultTimezone)));
		response.getSys().setSunsetTime(
				this.localDateTime(response.getSys().getSunset(), timezone.orElse(this.defaultTimezone)));

		// Set weather.description and weather.icon_url
		Optional<Weather> weather = response.getWeather().stream().findFirst();
		if (weather.isPresent()) {
			weather.get().setDescription(StringUtils.capitalize(weather.get().getDescription()));
			weather.get().setIconURL(StringUtils.replace(this.iconUrlTemplate, "{icon}", weather.get().getIcon()));
		}

		return response;
	}

	private String buildApiUri(String lat, String lon, String units, String lang) {
		return this.url + "?lat=" + lat + "&lon=" + lon + "&appid=" + this.apiKey + "&units=" + units + "&lang=" + lang;
	}

	/*
	 * @param seconds
	 * 
	 * @param timezone
	 * 
	 * @return LocalDateTime representation of Instant using seconds from the epoch
	 * of 1970-01-01T00:00:00Z.
	 */
	private LocalDateTime localDateTime(Long seconds, String timezone) {
		return Instant.ofEpochSecond(seconds).atZone(ZoneId.of(timezone)).toLocalDateTime();
	}

	private String maskQueryParam(String uri, String queryParam) {
		return UriComponentsBuilder.fromUriString(uri).replaceQueryParam(queryParam, StringUtils.repeat("*", 10))
				.toUriString();
	}

}
