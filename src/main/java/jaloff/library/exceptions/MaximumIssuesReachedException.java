package jaloff.library.exceptions;

public class MaximumIssuesReachedException extends RuntimeException {

	public MaximumIssuesReachedException(int max) {
		super("User reached maximum issues count: " + max);
	}
}
