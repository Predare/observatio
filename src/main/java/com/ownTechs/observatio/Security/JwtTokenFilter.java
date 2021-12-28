package com.ownTechs.observatio.Security;

import com.ownTechs.observatio.Service.Default.UserDefaultDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDefaultDetailsService userDefaultDetailsService;

    public final static String COOKIE_NAME = "authentication_token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate

        String token = null;
        Logger logger = Logger.getLogger("TokenValidationLogger");
        for (Cookie cookie : cookies) {
            logger.log(Level.INFO, "Cookie name: " + cookie.getName());
            if(cookie.getName().equals(JwtTokenFilter.COOKIE_NAME) ){
                token = cookie.getValue();
                logger.log(Level.INFO, "Cookie value: " + cookie.getValue());
            }
            
        }
        
        //Token isn't exist
        if(token == null){
            logger.log(Level.SEVERE, "Token isn't exist:");
            chain.doFilter(request, response);
            return;
        }
        
        logger.log(Level.SEVERE, jwtTokenUtil.getUsername(token) + " " + !jwtTokenUtil.validate(token));
        //Wrong token
        if (!jwtTokenUtil.validate(token)) {
            logger.log(Level.SEVERE, "Wrong token: " + token);
            chain.doFilter(request, response);
            return;
        }
        
        // Get user identity and set it on the spring security context
        UserDetails userDetails = userDefaultDetailsService
        .loadUserByUsername(jwtTokenUtil.getUsername(token));

        
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}