package com.hackaton.hackton.filter;

import com.hackaton.hackton.service.impl.JwtTokenService;
import com.hackaton.hackton.service.impl.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private JwtTokenService tokenService;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            authenticateUserFromToken(request, response);
            filterChain.doFilter(request, response);
    }

    private void authenticateUserFromToken(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            return;
        }

        var username = tokenService.validateToken(token).getSubject();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
