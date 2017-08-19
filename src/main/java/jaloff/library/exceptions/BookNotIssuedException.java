package jaloff.library.exceptions;

public class BookNotIssuedException extends RuntimeException {

	public BookNotIssuedException(Long bookId) {
		super("Book with id: " + bookId + " is not issued");
	}
}
