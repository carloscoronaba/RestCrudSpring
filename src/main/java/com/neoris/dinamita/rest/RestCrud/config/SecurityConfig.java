package com.neoris.dinamita.rest.RestCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("carlos.neoris")
                .password(passwordEncoder().encode("carlos123"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("marco.neoris")
                .password(passwordEncoder().encode("marco123"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.GET,"/crud/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/crud/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/crud/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/games/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/crud/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/games/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/crud/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/games/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
