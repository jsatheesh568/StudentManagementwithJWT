package com.studentmangement.StudentManagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called when an authentication error occurs.
     *
     * @param request  - the HTTP request.
     * @param response - the HTTP response.
     * @param authException - the exception thrown when authentication fails.
     * @throws IOException      - if an input/output error occurs.
     * @throws ServletException - if a servlet-related error occurs.
     */
    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // Set the response status as 401 Unauthorized
        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token is missing or invalid");
    }
}
