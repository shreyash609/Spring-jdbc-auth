package in.ashokit.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JdbcAuthenticateConfig {
		
		private static final String ADMIN = "ADMIN";
		private static final String USER = "USER";

		@Autowired
		private DataSource dataSource;

		@Autowired
		public void authManager(AuthenticationManagerBuilder auth) throws Exception {
		    auth.jdbcAuthentication()//for enabling jdbcAuthentication
		      	.dataSource(dataSource)//passing datasource object to tell with this we have to perform the operations
		      	.passwordEncoder(new BCryptPasswordEncoder())//encrypt the password and then comapre with encrpyted pwd in db
		      	.usersByUsernameQuery("select username,password,enabled from users where username=?")//getting username and pwd from user based on username
		      	.authoritiesByUsernameQuery("select username,authority from authorities where username=?");//getting the authorization role based on username
		}
		
		@Bean
		public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
				
			http.authorizeHttpRequests( (req) -> req
					.requestMatchers("/admin").hasRole(ADMIN)
					.requestMatchers("/user").hasAnyRole(ADMIN,USER)
					.requestMatchers("/").permitAll()
					.anyRequest().authenticated()
			).formLogin();
			
			return http.build();
		
		}

}
