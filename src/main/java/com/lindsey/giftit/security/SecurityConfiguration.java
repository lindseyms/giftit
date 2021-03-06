package com.lindsey.giftit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImplementation userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImplementation userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/profile").hasRole("USER")
                .antMatchers("/add_items").hasRole("USER")
                .antMatchers("/friends").hasRole("USER")
                .antMatchers("/search").hasRole("USER")
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/profile", true)
                .usernameParameter("email");

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
