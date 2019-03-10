package com.abcwebportal.webportal.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abcwebportal.webportal.security.CustomAuthenticationEntryPoint;
import com.abcwebportal.webportal.security.SecurityConstants;
import com.abcwebportal.webportal.security.jwt.AuthenticationFilter;
import com.abcwebportal.webportal.serviceimpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserServiceImpl userService;
	// to encrypt the password i.e default will be plain text
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public AuthenticationFilter jwtAuthenticationFilter() {
		return new AuthenticationFilter();
	}

	/**
	 * <p>
	 * To return the instance of <code>AuthenticationManager</code>
	 */
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(this.customAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll().antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll().anyRequest().authenticated();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
