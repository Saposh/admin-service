package com.admin_service.demo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        // Логируем URI запроса
//        System.out.println("Incoming request: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found. Proceeding without authentication.");
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
//        System.out.println("Extracted token: " + token);

        if (!jwtUtil.validateToken(token)) {
//            System.out.println("Token validation failed.");
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.extractClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
//        System.out.println("Extracted email: " + email + ", role: " + role);

        // Создаем список авторитетов, используя роль
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        User userDetails = new User(email, "", authorities);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Устанавливаем аутентификацию в SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authToken);
//        System.out.println("User authenticated: " + email);

        chain.doFilter(request, response);
    }
}
