package com.budgetfriendly.bms.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	
	 @Bean
	 @Override
	 protected AuthenticationManager authenticationManager() throws Exception {
	      return super.authenticationManager();
	 }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		http.csrf().disable();
	}
	  
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**"); 
    }
//	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				
//				registry.addMapping("/**")
//						.allowedMethods("GET", "OPTIONS", "POST")
//						.allowedOrigins("*")
//						.allowedHeaders("*");
//			}
//		};
//	}
	
	
}
