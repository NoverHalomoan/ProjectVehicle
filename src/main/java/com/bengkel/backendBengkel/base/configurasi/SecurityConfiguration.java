package com.bengkel.backendBengkel.base.configurasi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bengkel.backendBengkel.base.exception.CustomeAuthenticationEntryPoint;
import com.bengkel.backendBengkel.base.exception.CustomeDeniedHandler;
import com.bengkel.backendBengkel.base.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    private final CustomeDeniedHandler customeDeniedHandler;

    private final UserDetailsService userDetailsService;

    private final CustomeAuthenticationEntryPoint customeAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth
                        -> auth
                        .requestMatchers("/api/auth/employee/**").permitAll()
                        .requestMatchers("/api/v1/employee/**").authenticated()
                        .requestMatchers("/api/v1/Kendaraan/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/file/**").authenticated()
                        .requestMatchers("/api/v2/message/**").authenticated()
                        .anyRequest().denyAll())
                .exceptionHandling(ex
                        -> ex.accessDeniedHandler((AccessDeniedHandler) customeDeniedHandler)
                        .authenticationEntryPoint((AuthenticationEntryPoint) customeAuthenticationEntryPoint))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // .requestMatchers("/api/profile/employee/**").authenticated()
        // .httpBasic(Customizer.withDefaults())
        //         .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
