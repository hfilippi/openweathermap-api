package ar.com.vampiro.openweathermapapi.error;

public class WeatherApiCallException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WeatherApiCallException() {
		this("Cannot get weather");
	}

	public WeatherApiCallException(String message) {
		super(message);
	}

}
