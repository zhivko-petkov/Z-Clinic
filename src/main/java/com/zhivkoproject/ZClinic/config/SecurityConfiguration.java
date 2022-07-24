package com.zhivkoproject.ZClinic.config;

import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.service.impl.ZClinicUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.  requestCache().disable(). //define which requests are allowed and which not
                authorizeRequests().
                //everyone can download static resources (css, js, images)
                //vnimavai tuk, papkata trqbva da se kazva images
                        antMatchers("/users/").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_DOCTOR')").
                antMatchers("/users/add", "/tests/add", "/tests/edit").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_DOCTOR')").
                antMatchers("/", "/users/login", "/users/register", "/tests").permitAll().
                        antMatchers("/css/**", "/images/**").permitAll().
                        antMatchers("/user-photos/**").authenticated().

                //antMatchers("/users").hasAnyRole("ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_DOCTOR").
                //antMatchers("/users/edit").hasAnyRole("ROLE_ADMIN", "ROLE_MODERATOR").
                        //requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                //everyone can login and register

                // all other pages are available for logged users
                        anyRequest().
                authenticated().
                and().
                // configuration of form login
                        formLogin().
                // the custom login form
                        loginPage("/users/login").
                // the name of the username field
                        usernameParameter("username").
                // the name of the password field
                        passwordParameter("password").
                // where redirect when user is logged
                        defaultSuccessUrl("/").
                // where to go in case that login failed
                        failureForwardUrl("/users/login?error=true").
                and().
                // congifure logout
                        logout().
                // which is the logout url
                        logoutUrl("/users/logout").
                        logoutSuccessUrl("/").
                // invalidate the session and delete
                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");

        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new ZClinicUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }


}
