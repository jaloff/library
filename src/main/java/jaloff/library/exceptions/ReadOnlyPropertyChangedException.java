package jaloff.library.exceptions;

public class ReadOnlyPropertyChangedException extends RuntimeException {
	
	public ReadOnlyPropertyChangedException(String propertyName, String expected, String recived) {
		super("Property '" + propertyName + "' is read-only, exclude it from your request "
				+ "or send unchanged\n expected value '" + expected + "', but recieved '" + recived +"'");
	}
}
