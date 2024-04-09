package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Clouds {

	@JsonProperty("all")
	private Integer all;

}
