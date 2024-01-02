package com.curso.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.curso.ecommerce.model.Usuario;

@Configuration
@EnableWebSecurity
public class SpringBootSecurity {
	
	@Bean
	UserDetailsServiceImpl userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(getEncoder());
		return authProvider;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/usuario/login").permitAll()
				.requestMatchers("/usuario/registro").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/vendor/**").permitAll()
				.requestMatchers("/administrador/**").hasAnyAuthority("admin")
				.requestMatchers("/productos/**").hasAnyAuthority("admin")
				.requestMatchers("/usuario/**").hasAnyAuthority("usuario")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/usuario/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/usuario/acceder", true)
				.failureUrl("/usuario/login?error=true")
			);
		return http.build();
	}
    

}
