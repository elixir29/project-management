package com.piy.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery("select username, password, enabled from user_accounts where username = ?")
			.authoritiesByUsernameQuery("select username, role, enabled from user_accounts where username = ?")
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	//Specifying the things the logged in user can do. (Basically the authorization part of the user).
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/projects/new").hasRole("ADMIN")
			.antMatchers("/projects/update").hasRole("ADMIN")
			.antMatchers("/projects/save").hasRole("ADMIN")
			.antMatchers("/projects/delete").hasRole("ADMIN")
			
			.antMatchers("/employees/new").hasRole("ADMIN")
			.antMatchers("/employees/update").hasRole("ADMIN")
			.antMatchers("/employees/save").hasRole("ADMIN")
			.antMatchers("/employees/delete").hasRole("ADMIN")
			
			.antMatchers("/", "/**").permitAll()
			
			.and()
			.csrf().ignoringAntMatchers("/app-api/employees/**").ignoringAntMatchers("/app-api/projects/**")
				   .ignoringAntMatchers("/spring-rest-projects/**").ignoringAntMatchers("/spring-rest-employees/**")
			.and()
			.formLogin()
			
			.and()
			.logout()
				.logoutSuccessUrl("/");
	}
	
}