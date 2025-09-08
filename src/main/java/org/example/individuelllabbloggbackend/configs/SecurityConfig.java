package org.example.individuelllabbloggbackend.configs;

import org.example.individuelllabbloggbackend.converters.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .headers(h-> h.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())) // för att h2.console ska funka
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers("/h2-console/**").permitAll()  // behövs också för att h2.console ska funka
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2->
                        oauth2
                                .jwt(jwt-> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );
        return http.build();
    }

}
