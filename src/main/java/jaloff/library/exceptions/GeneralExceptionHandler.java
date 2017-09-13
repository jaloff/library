package jaloff.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ReadOnlyPropertyChangedException.class)
	public String readOnlyPropertyChangedExceptionHandler(Exception e) {
		return e.getMessage();
	}
}
