package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Main {

	@JsonProperty("temp")
	private Double temp;

	@JsonProperty("feels_like")
	private Double feelsLike;

	@JsonProperty("temp_min")
	private Double tempMin;

	@JsonProperty("temp_max")
	private Double tempMax;

	@JsonProperty("pressure")
	private Integer pressure;

	@JsonProperty("humidity")
	private Integer humidity;

	@JsonProperty("sea_level")
	private Integer seaLevel;

	@JsonProperty("grnd_level")
	private Integer grndLevel;

}
