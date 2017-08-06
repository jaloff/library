package jaloff.library.services;

import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class SimplePasswordGenerator implements PasswordGenerator {

	@Override
	public String generate(int length) {
		return IntStream.range(0, length)
				.map(i -> (int)Math.floor(Math.random() * 36))
				.map(i -> i < 10 ? i + 48 : i + 87)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

}
