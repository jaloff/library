package jaloff.library.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class JustLogMailSender implements MailSender {

	private static final Logger log = LoggerFactory.getLogger(JustLogMailSender.class);
	
	@Override
	public void send(SimpleMailMessage mail) throws MailException {
		log.info(mail.toString());
	}

	@Override
	public void send(SimpleMailMessage... mails) throws MailException {
		Arrays.stream(mails).map(SimpleMailMessage::toString).forEach(s -> log.info(s));
	}

}
