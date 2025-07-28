package com.bengkel.backendBengkel.base.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtilFilter jwtUtilFilter;

    private final CustomUserDetailsService customeUserDetailService;

    public JwtAuthFilter(JwtUtilFilter jwtUtilFilter, CustomUserDetailsService customeUserDetailService) {
        this.jwtUtilFilter = jwtUtilFilter;
        this.customeUserDetailService = customeUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String idUser = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            idUser = jwtUtilFilter.extractUsername(jwt, "login");
        }
        if (idUser != null) {
            UserDetails employeeDetails = customeUserDetailService.loadUserByUsername(idUser);
            if (jwtUtilFilter.validateToken(idUser, jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(employeeDetails, null, employeeDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
