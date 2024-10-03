package ar.com.vampiro.openweathermapapi.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.com.vampiro.openweathermapapi.error.WeatherApiCallException;
import ar.com.vampiro.openweathermapapi.model.TempResponse;
import ar.com.vampiro.openweathermapapi.model.Weather;
import ar.com.vampiro.openweathermapapi.model.WeatherResponse;
import ar.com.vampiro.openweathermapapi.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * OpenWeather Rest API made easy.
 * 
 * @see <link>https://openweathermap.org/current#data</link>
 * @author hfilippi
 */

@Slf4j
@Service
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

	@Value("${openweathermap-org.default-location.latitude}")
	private Double defaultLocationLatitude;

	@Value("${openweathermap-org.default-location.longitude}")
	private Double defaultLocationLongitude;

	private static final String SUNRISE_SUNSET_TIME_FORMAT = "HH:mm";

	@Override
	@CacheEvict(value = "weather", allEntries = true)
	@Scheduled(fixedRateString = "${openweathermap-org.cache.ttl}")
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

	@Override
	public TempResponse temp(Optional<Double> latitude, Optional<Double> longitude) {
		WeatherResponse weatherResponse = this.weather(latitude.orElse(this.defaultLocationLatitude),
				longitude.orElse(this.defaultLocationLongitude), Optional.empty(), Optional.empty(), Optional.empty())
				.block();

		if (Objects.isNull(weatherResponse)) {
			throw new WeatherApiCallException();
		}

		Optional<Weather> weather = weatherResponse.getWeather().stream().findFirst();
		if (weather.isEmpty()) {
			throw new WeatherApiCallException();
		}

		// @formatter:off
		return TempResponse.builder()
				.temp(Math.ceil(weatherResponse.getMain().getTemp()))
				.tempMin(Math.ceil(weatherResponse.getMain().getTempMin()))
				.tempMax(Math.ceil(weatherResponse.getMain().getTempMax()))
				.weatherDesc(weather.get().getDescription())
				.weatherIcon(weather.get().getIcon())
				.location(weatherResponse.getName())
				.build();
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
