package com.coco.bakingbuddy.auth.config;

import com.coco.bakingbuddy.auth.service.PrincipalService;
import com.coco.bakingbuddy.jwt.filter.JwtAuthenticationFilter;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalService principalService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF protection disabled
                .cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/app/**").permitAll()
                        .requestMatchers("/topic/**").permitAll()
                        .requestMatchers("/api/refresh-token").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("accessToken", "refreshToken")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                        )
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(principalService) // 사용자 정보 서비스 설정
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}