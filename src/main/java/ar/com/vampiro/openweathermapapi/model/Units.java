package ar.com.vampiro.openweathermapapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Units {

	METRIC("°C"), IMPERIAL("°F"), STANDARD("°K");

	private String symbol;

}
