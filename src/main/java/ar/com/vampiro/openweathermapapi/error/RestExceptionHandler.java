package ar.com.vampiro.openweathermapapi.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(WeatherApiCallException.class)
	public ResponseEntity<ErrorMessage> apiCallExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = buildErrorMessage(HttpStatus.CONFLICT.value(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = buildErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorMessage buildErrorMessage(int httpStatusCode, String message, String description) {
		// @formatter:off
		return ErrorMessage.builder()
				.statusCode(httpStatusCode)
				.message(message)
				.description(description)
				.timestamp(new Date())
				.build();
		// @formatter:on

	}

}
