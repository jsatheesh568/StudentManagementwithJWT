package com.studentmangement.StudentManagement.config;

import com.studentmangement.StudentManagement.security.JwtAuthenticationEntryPoint;
import com.studentmangement.StudentManagement.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  /**
   * Security filter chain to configure security settings for HTTP requests.
   *
   * @param http - HttpSecurity to configure security settings
   * @return - Configured SecurityFilterChain
   * @throws Exception - If any error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Disable CSRF protection, typically done for stateless APIs (JWT-based)
    http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/auth/login").permitAll() // Allow login without authentication
            .requestMatchers("/students/**").hasAnyRole("USER", "ADMIN") // Only allow users with USER or ADMIN role
            .requestMatchers("/admin/**").hasRole("ADMIN") // Only allow users with ADMIN role
            .anyRequest().authenticated() // Require authentication for any other request
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Custom entry point for 401 Unauthorized
            .and()
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before authentication

    return http.build(); // Return the security filter chain
  }
}
