package jaloff.library.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookExceptionHandler {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(BookNotFoundException.class)
	@ResponseBody
	public String bookNotFoundHandler(HttpServletRequest req, Exception e) {
		return e.getMessage();
	}
}
