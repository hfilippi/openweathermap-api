package ar.com.vampiro.openweathermapapi.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.vampiro.openweathermapapi.error.WeatherApiCallException;
import ar.com.vampiro.openweathermapapi.model.Weather;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import ar.com.vampiro.openweathermapapi.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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

	private static final String SUNRISE_SUNSET_TIME_FORMAT = "HH:mm";

	@Override
	@Cacheable(value = "weather-cache")
	public Mono<WeatherResponse> weather(Double latitude, Double longitude, Optional<String> units,
			Optional<String> lang, Optional<String> timezone) {
		// @formatter:off
		return WebClient.create()
				.get()
				.uri(this.url, uri -> uri.queryParam("lat", String.valueOf(latitude))
						.queryParam("lon", String.valueOf(longitude))
						.queryParam("appid", this.apiKey)
						.queryParam("units", units.orElse(this.defaultUnits))
						.queryParam("lang", lang.orElse(this.defaultLang))
						.build())
				.retrieve()
				.bodyToMono(WeatherResponse.class)
				.doOnSuccess(body -> {
					// Set sunrise_time and sunset_time
					body.getSys().setSunriseTime(
							this.getSunriseSunsetTime(body.getSys().getSunrise(), timezone.orElse(this.defaultTimezone)));
					body.getSys().setSunsetTime(
							this.getSunriseSunsetTime(body.getSys().getSunset(), timezone.orElse(this.defaultTimezone)));
					// Set weather.description and weather.icon_url
					Optional<Weather> weather = body.getWeather().stream().findFirst();
					if (weather.isPresent()) {
						weather.get().setDescription(StringUtils.capitalize(weather.get().getDescription()));
						weather.get().setIconURL(StringUtils.replace(this.iconUrlTemplate, "{icon}", weather.get().getIcon()));
					}

					log.info(body.toString());
				})
				// Error handling
				.onErrorMap(Throwable.class, t -> new WeatherApiCallException());
		// @formatter:on
	}

	/*
	 * @param seconds
	 * 
	 * @param timezone
	 * 
	 * @return LocalDateTime representation of Instant using seconds from the epoch
	 * of 1970-01-01T00:00:00Z.
	 */
	private String getSunriseSunsetTime(Long seconds, String timezone) {
		return DateTimeFormatter.ofPattern(SUNRISE_SUNSET_TIME_FORMAT)
				.format(Instant.ofEpochSecond(seconds).atZone(ZoneId.of(timezone)).toLocalDateTime());
	}

}
