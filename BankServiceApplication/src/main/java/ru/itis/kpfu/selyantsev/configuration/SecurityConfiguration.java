package ru.itis.kpfu.selyantsev.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.kpfu.selyantsev.filter.JwtFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/v1/users/registration").permitAll()
                        .requestMatchers("/api/auth/login", "/api/auth/token").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();
    }
}
