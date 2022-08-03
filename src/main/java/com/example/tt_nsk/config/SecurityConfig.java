package com.example.tt_nsk.config;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/").permitAll();
                    requests.antMatchers("//api/v1/player").permitAll();
                    requests.antMatchers(HttpMethod.GET, "/player").hasRole("ADMIN");
                    requests.antMatchers(HttpMethod.POST, "/player").hasRole("ADMIN");
                    requests.mvcMatchers(HttpMethod.GET, "/player/{playerId}").permitAll();
                }
        );

        http.exceptionHandling().accessDeniedPage("/access-denied");
        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll();
        http.logout()
                .logoutSuccessUrl("/player/all")
                .permitAll();
        http.httpBasic();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
