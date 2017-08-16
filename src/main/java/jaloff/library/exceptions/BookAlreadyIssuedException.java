package jaloff.library.exceptions;

public class BookAlreadyIssuedException extends RuntimeException {
	
	public BookAlreadyIssuedException(long bookId) {
		super("Book with id: " + bookId + " is issued");
	}
}
