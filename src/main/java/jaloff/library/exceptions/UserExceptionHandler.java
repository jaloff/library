package jaloff.library.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(HttpServletRequest rq, Exception e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UserWithEmailExistException.class)
	public String handleUserWithEmailExistException(HttpServletRequest rq, Exception e) {
		return e.getMessage();
	}
}
