package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sys {

	@JsonProperty("type")
	private Integer type;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("country")
	private String country;

	@JsonProperty("sunrise")
	private Long sunrise;

	@JsonProperty("sunset")
	private Long sunset;

	@JsonProperty("sunrise_time")
	private String sunriseTime;

	@JsonProperty("sunset_time")
	private String sunsetTime;

}
