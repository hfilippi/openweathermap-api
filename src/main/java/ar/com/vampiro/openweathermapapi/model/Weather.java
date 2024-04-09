package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Weather {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("main")
	private String main;

	@JsonProperty("description")
	private String description;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("icon_url")
	private String iconURL;

}
