package jaloff.library.services;

public interface PasswordGenerator {
	int DEFAULT_LENGTH = 8;
	String generate(int length);
	default String generate() {
		return generate(DEFAULT_LENGTH);
	}
}
