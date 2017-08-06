package jaloff.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
	@Profile("prod")
	@Bean
	public MailSender mailSender() {
		return new JavaMailSenderImpl(); // Configure for real mail sending
	}
}
