package com.example.xchange.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.example.xchange.service.UserService;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(GET, "/api/games").permitAll()
                .antMatchers(GET, "/api/games/search").permitAll()
                .antMatchers(GET, "/api/games/all").hasAnyRole("ADMIN", "USER")
                .antMatchers(PUT, "/api/games").hasRole("ADMIN")
                .antMatchers(POST, "/api/games").hasRole("ADMIN")
                .antMatchers(GET, "/api/cart").hasAnyRole("ADMIN", "USER")
                .antMatchers(POST, "/api/cart").hasRole("USER")
                .antMatchers(DELETE, "/api/cart/{id}").hasRole("USER")
                .antMatchers("/api/exchange/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/process-payment").authenticated()
                .antMatchers(POST, "/api/payment-event-handler").permitAll()
                .antMatchers(GET, "/api/promo/{promo}").authenticated()
                .antMatchers(POST, "/api/promo").hasRole("ADMIN")
                .antMatchers(GET, "/api/games/{gameId}/reviews").authenticated()
                .antMatchers(POST, "/api/games/{gameId}/reviews").authenticated()
                .antMatchers(DELETE, "/api/games/{gameId}/reviews/{reviewId}").hasRole("USER")
                .antMatchers(GET, "/api/user/me").authenticated()
                .antMatchers(GET, "/api/user/{username}").authenticated()
                .antMatchers(GET, "/api/user/{username}/reviews").authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
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
}
