package com.studentmangement.StudentManagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  @Autowired private JwtTokenProvider jwtTokenProvider;

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // Extract the token by removing "Bearer " prefix
    }
    return null;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Extract the JWT token from the Authorization header
    String token = getTokenFromRequest(request);

    if (token != null && jwtTokenProvider.validateToken(token)) {
      // Get authentication from the token
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext()
          .setAuthentication(authentication); // Set the authentication in the Security context
    }

    filterChain.doFilter(request, response); // Continue filter chain
  }
}
