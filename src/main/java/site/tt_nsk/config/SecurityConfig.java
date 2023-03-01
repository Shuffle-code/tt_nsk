package site.tt_nsk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
                .antMatchers(HttpMethod.POST, "/upcomingTournaments/enroll/{{playerId}}/{{tournamentId}}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/enroll/{{playerId}}/{{tournamentId}}").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/disenroll/{playerId}/{tournamentId}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/tournaments/{playerId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/tournaments/{playerId}").permitAll()
                .antMatchers(HttpMethod.GET, "/upcomingTournaments/all").permitAll()
                .antMatchers(HttpMethod.PUT, "/upcomingTournaments/all").permitAll()
                .antMatchers(HttpMethod.POST, "/tour/**").permitAll()
                .and().csrf().disable();
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
}

