package jaloff.library.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
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
				.denyAll()
			.and()
				.httpBasic();
		http
			.csrf()
			.disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT email, password, true FROM users WHERE email=?")
			.authoritiesByUsernameQuery("SELECT email, role FROM users WHERE email=?");
	}

}
