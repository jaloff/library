package jaloff.library.exceptions;

public class IssueNotFoundException extends RuntimeException {

	public IssueNotFoundException(long id) {
		super("Borrowing with id:" + id + " not found" );
	}
}
