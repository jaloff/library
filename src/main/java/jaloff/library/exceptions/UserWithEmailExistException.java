package jaloff.library.exceptions;

public class UserWithEmailExistException extends RuntimeException {
	public UserWithEmailExistException(String email) {
		super("User with email:" + email + " already exist");
	}
}
