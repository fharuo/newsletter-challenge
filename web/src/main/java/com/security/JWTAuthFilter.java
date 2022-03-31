package com.security;

import com.service.serviceImpl.SubscriberServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final SubscriberServiceImpl subscriberService;

    public JWTAuthFilter(JWTService jwtService, SubscriberServiceImpl subscriberService) {
        this.jwtService = jwtService;
        this.subscriberService = subscriberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.isValid(token);

            if (isValid) {
                String appUserLogin = jwtService.getAppUserLogin(token);
                UserDetails userDetails = subscriberService.loadUserByUsername(appUserLogin);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
