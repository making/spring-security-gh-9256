package com.example.demojwttest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequests -> authorizeRequests
						.mvcMatchers(HttpMethod.GET, "/messages/**").hasAuthority("SCOPE_message:read")
						.anyRequest().authenticated()
				)
				.oauth2ResourceServer(r -> r.jwt());
	}

}