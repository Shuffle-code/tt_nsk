package com.example.tt_nsk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/upcomingTournaments/enroll/{{playerId}}/{{tournamentId}}").permitAll()
                .antMatchers(HttpMethod.POST, "/upcomingTournaments/enroll/{{playerId}}/{{tournamentId}}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/enroll/{{playerId}}/{{tournamentId}}").permitAll()
                //.antMatchers(HttpMethod.POST, "/upcomingTournaments/enroll/{playerId}/{tournamentId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/disenroll/{playerId}/{tournamentId}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/tournaments/{playerId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/tournaments/{playerId}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/all").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/all").permitAll()
                .and().csrf().disable()
        ;

 /*
                //(requests) -> {
                //.antMatchers(HttpMethod.PUT, "/upcomingTournaments/enroll/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/upcomingTournaments/all").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/tournaments/**").permitAll()

                //.hasAnyAuthority("player.create", "player.update", "player.read")
                //.antMatchers(HttpMethod.PUT, "/upcomingTournaments/disenroll/").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("//api/v1/player").permitAll()
//                    requests.antMatchers(HttpMethod.GET, "/player").hasRole("ADMIN");
                .antMatchers(HttpMethod.GET, "/player").hasAnyAuthority("player.create", "player.update", "player.read")
//                    requests.antMatchers(HttpMethod.POST, "/player").hasRole("ADMIN");
                .antMatchers(HttpMethod.POST, "/player").hasAnyAuthority("player.create", "player.update", "player.read")
//                    requests.antMatchers(HttpMethod.POST, "/tour").hasRole("ADMIN");
//                    requests.antMatchers(HttpMethod.GET, "/tour").hasRole("ADMIN");
                .mvcMatchers(HttpMethod.GET, "/player/{playerId}").permitAll()
                .and().csrf().disable()
        ;
        //}
        // );
*/

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
/*
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .and()
                .withUser("guest")
                .password(passwordEncoder().encode("guest"))
                .roles("GUEST");
    }

 */



    }

