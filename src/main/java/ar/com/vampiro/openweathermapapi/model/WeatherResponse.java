package ar.com.vampiro.openweathermapapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class WeatherResponse {

	@JsonProperty("coord")
	private Coord coord;

	@JsonProperty("weather")
	private List<Weather> weather;

	@JsonProperty("base")
	private String base;

	@JsonProperty("main")
	private Main main;

	@JsonProperty("visibility")
	private Integer visibility;

	@JsonProperty("wind")
	private Wind wind;

	@JsonProperty("rain")
	private Rain rain;

	@JsonProperty("clouds")
	private Clouds clouds;

	@JsonProperty("dt")
	private Integer dt;

	@JsonProperty("sys")
	private Sys sys;

	@JsonProperty("timezone")
	private Integer timezone;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cod")
	private Integer cod;

}
