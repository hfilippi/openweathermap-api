package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TempResponse {

	@JsonProperty("temp")
	private Double temp;

	@JsonProperty("temp_min")
	private Double tempMin;

	@JsonProperty("temp_max")
	private Double tempMax;

	@JsonProperty("weather_desc")
	private String weatherDesc;

	@JsonProperty("weather_icon")
	private String weatherIcon;

	@JsonProperty("location")
	private String location;

}
