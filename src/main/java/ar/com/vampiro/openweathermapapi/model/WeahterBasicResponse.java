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
public class WeahterBasicResponse {

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("temp")
	private Double temp;

	@JsonProperty("feels_like")
	private Double feelsLike;

	@JsonProperty("icon")
	private String icon;

}
