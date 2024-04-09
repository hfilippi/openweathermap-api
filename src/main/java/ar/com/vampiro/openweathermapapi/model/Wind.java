package ar.com.vampiro.openweathermapapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wind {

	@JsonProperty("speed")
	private Double speed;

	@JsonProperty("deg")
	private Integer deg;

	@JsonProperty("gust")
	private Double gust;

}
