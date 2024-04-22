package ru.nvkz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static ru.nvkz.entity.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())//нужно отключить, поговорим отдельно дальше
                .authorizeHttpRequests(urlConfig ->
                        urlConfig
                                .requestMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                // .requestMatchers("/users/{\\d+}/delete").hasAnyRole(ADMIN.getAuthority())
                                .requestMatchers("/properties/**").hasAuthority(ADMIN.getAuthority())
                                .requestMatchers("/string-classifiers/**").hasAuthority(ADMIN.getAuthority())
                                .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(mySimpleUrlAuthenticationSuccessHandler)
                );
        return http.build();
    }

}