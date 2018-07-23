package com.garov.library.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("admin")
                .password("nimda")
                .roles("ADMIN", "USER")
                .and()
                .withUser("user")
                .password("123")
                .roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {// @formatter:off
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/library/book/**").hasRole("USER")
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .logout();
    }
}
