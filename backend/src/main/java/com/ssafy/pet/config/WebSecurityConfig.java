package com.ssafy.pet.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		http
//		.httpBasic().and().authorizeRequests().
		// HttpServletRequest 요청 URL에 따라 접근 권한을 설정합니다. 
		// /admin/*요청은 ADMIN 권한을 가진 사용자만 접근 가능하다
//		antMatchers("/admin/**").hasRole("ADMIN")
		// /user/*요청은 USER권한을 가진 사용자만 접근 가능하다
//		.antMatchers("/user/**").hasRole("USER")
		// /** 요청은 권한없이 접근이 가능하다
//		.antMatchers("/**").permitAll();
		// admin 이나 user url로 들어오는 요청에 대한 인증을 필요로 한다는 말이야
		// ?이게 구지 필요할까? 망할 이거때문에 user Controller를 못쓰네?
//		security.httpBasic().disable();

//		http.cors().and();
//		http.csrf().disable();

		http.csrf().disable().cors().disable().headers().frameOptions().disable();
//		super.configure(http);
	}

}
