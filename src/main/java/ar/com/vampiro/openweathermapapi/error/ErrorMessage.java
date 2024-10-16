package ar.com.vampiro.openweathermapapi.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

	@JsonProperty("status_code")
	private int statusCode;

	@JsonProperty("message")
	private String message;

	@JsonProperty("description")
	private String description;

	@JsonProperty("timestamp")
	private Date timestamp;

}
