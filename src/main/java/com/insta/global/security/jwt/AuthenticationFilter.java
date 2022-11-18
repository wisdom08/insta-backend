package com.insta.global.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = this.tokenProvider.resolveToken((HttpServletRequest)request);
        if (token != null && this.tokenProvider.validateToken(token)) {
            Authentication authentication = this.tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public AuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
