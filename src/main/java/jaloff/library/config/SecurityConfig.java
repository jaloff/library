package jaloff.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.DELETE, "/books/*")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/books")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/books")
				.hasRole("ADMIN")
			.and()
				.authorizeRequests()
				.antMatchers("/users", "/users/*")
				.hasRole("ADMIN")
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/books", "/books/*")
				.permitAll()
			.and()
				.authorizeRequests()
				.anyRequest()
				.denyAll();
		http
			.csrf()
			.disable();
	}

}
