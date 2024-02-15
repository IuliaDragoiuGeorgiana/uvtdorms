package com.uvtdorms.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;
    private final List<String> publicEndpoints = Arrays.asList(
            "/api/auth/login",
            "/api/dorms/dorms-names",
            "/api/auth/register-student",
            "/api/rooms/get-rooms-numbers-from-dorm/**");

    private final AntPathMatcher pathMatcher;

    public JwtAuthFilter(UserAuthProvider userAuthProvider) {
        this.userAuthProvider = userAuthProvider;
        this.pathMatcher = new AntPathMatcher();
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        boolean isPublicEndpoint = publicEndpoints.stream().anyMatch(uri -> pathMatcher.match(uri, requestURI));

        if (!isPublicEndpoint) {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header != null) {
                String[] authElements = header.split(" ");
                if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                    try {
                        if ("GET".equals(request.getMethod())) {
                            SecurityContextHolder.getContext()
                                    .setAuthentication(userAuthProvider.validateToken(authElements[1]));
                        } else {
                            SecurityContextHolder.getContext()
                                    .setAuthentication(userAuthProvider.validateTokenStrongly(authElements[1]));
                        }
                    } catch (RuntimeException e) {
                        SecurityContextHolder.clearContext();
                        throw e;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
