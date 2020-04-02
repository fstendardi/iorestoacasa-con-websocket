package com.stenda.websocketdemo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stenda.websocketdemo.service.CustomUserDetailService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
   private CustomUserDetailService userDetailsService;
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth
		//.jdbcAuthentication()
		//.dataSource(dataSource)
		//.usersByUsernameQuery("SELECT username, password, 1 FROM useraaa where username = ?")
		//.authoritiesByUsernameQuery("SELECT * FROM DUAL")	
		//.and()
		.userDetailsService(userDetailsService)
		;

	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
          .antMatchers("/admin").authenticated()
          .anyRequest().permitAll()
          .and()
          .httpBasic()
          .and()
          .logout()
          .logoutSuccessUrl("/")
          .and()
          .csrf().disable()
         // .authenticationEntryPoint(authenticationEntryPoint)
          ;
 
        //http.addFilterAfter(BasicAuthenticationFilter.class);
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
