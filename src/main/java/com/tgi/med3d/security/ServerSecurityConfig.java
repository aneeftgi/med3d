package com.tgi.med3d.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tgi.med3d.exception.CustomAuthenticationEntryPoint;

@Configuration
//@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {


	
    private final UserDetailsService userDetailsService;

    public ServerSecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, @Qualifier("loginService")
            UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
//                .authorizeRequests()
//                .antMatcher("/usermanager/**")   
//                .antMatcher("/hospitalManager/**")            
//                .antMatcher("/rolemanager/**")              
                .authorizeRequests()
//                .antMatchers("/usermanager/getAllRoles").permitAll()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers("/oauth/authorize","/oauth/confirm_access","/oauth/token").permitAll()
                .antMatchers("/swagger-resources/**","/webjars/**", "/swagger-ui.html","/v2/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**","/webjars/**", "/swagger-ui.html","/v2/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .anyRequest().authenticated();;


    }


}