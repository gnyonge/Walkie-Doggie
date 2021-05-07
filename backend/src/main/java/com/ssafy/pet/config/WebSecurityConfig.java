package com.ssafy.pet.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		 http.httpBasic()
         .and()
         .authorizeRequests()
         .antMatchers("/admin/**").hasRole("ADMIN")
         .antMatchers("/user/**").hasRole("USER")
         .antMatchers("/**").permitAll();
		 //admin 이나 user url로 들어오는 요청에 대한 인증을 필요로 한다는 말이야 
		 //?이게 구지 필요할까? 망할 이거때문에 user Controller를 못쓰네?
		super.configure(http);
	}

}
