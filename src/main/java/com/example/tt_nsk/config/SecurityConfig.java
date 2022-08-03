package com.example.tt_nsk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((request) -> {
            request.antMatchers("/player/all").permitAll();
//            request.antMatchers("/").permitAll();//позволяет ходить всем на страницу без авторизации
//            request.antMatchers(HttpMethod.POST, "/product").hasAuthority("product.create");
//            request.antMatchers(HttpMethod.GET, "/product").hasAuthority("product.create");
            request.antMatchers(HttpMethod.GET, "/player//image/{id}").permitAll();
            request.antMatchers(HttpMethod.GET, "/player//images/{id}").permitAll();
            request.mvcMatchers(HttpMethod.GET,"/player/{playerId}").permitAll();
        });
        http.authorizeRequests((request) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)request.anyRequest()).authenticated();
        });
        http.exceptionHandling().accessDeniedPage("/access-denied");
        http.formLogin();
        http.httpBasic();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
