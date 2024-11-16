package com.example.webappvolunteer.config;

import com.example.webappvolunteer.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.example.webappvolunteer.enums.Role.VOLUNTEER;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка CORS
                .cors(cors->cors.disable())
//                        (cors -> cors
//                        .configurationSource(request -> {
//                            CorsConfiguration corsConfiguration = new CorsConfiguration();
//                            corsConfiguration.setAllowedOriginPatterns(List.of("*")); // Разрешаем все источники
//                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                            corsConfiguration.setAllowedHeaders(List.of("*")); // Разрешаем все заголовки
//                            corsConfiguration.setAllowCredentials(true);
//                            return corsConfiguration;
//                        })
//                )
                // Отключаем CSRF, если это допустимо
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/vol/register").permitAll() // Разрешаем доступ к ресурсу регистрации
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/vol/login").permitAll()
                        .requestMatchers("/get-guide/languages").permitAll()
                        .requestMatchers("Autorization.html").permitAll()
                        .requestMatchers("/Registration.html", "/app/**", "/js/**", "/css/**").permitAll() // Разрешаем доступ к HTML, JS и CSS
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Создание сессии при необходимости
                        .maximumSessions(1) // Максимальное количество сессий для одного пользователя
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Перенаправление после выхода
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider());

        return http.build(); // Возвращаем построенный объект HttpSecurity
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
