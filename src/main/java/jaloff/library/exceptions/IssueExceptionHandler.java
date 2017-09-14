package jaloff.library.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BookAlreadyIssuedException.class)
	public String bookAlreadyIssuedException(HttpServletRequest rq, Exception e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IssueNotFoundException.class)
	public String issueNotFoundException(HttpServletRequest rq, Exception e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BookNotIssuedException.class)
	public String bookNotIssuedException(Exception e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(MaximumIssuesReachedException.class)
	public String maximumIssuesReachedException(Exception e) {
		return e.getMessage();
	}
}
