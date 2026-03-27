package com.atria.userservice.security;

import com.atria.userservice.exception.ExpiredJwtCustomException;
import com.atria.userservice.exception.JwtCustomException;
import com.atria.userservice.exception.MalformedJwtCustomException;
import com.atria.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        boolean isBearerToken = Objects.nonNull(authorization) ? authorization.startsWith("Bearer ") : false;
        if (isBearerToken) {
            String jwtToken = authorization.substring(7);
            if (jwtService.isAccessToken(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            try {
                String username = jwtService.extractUsername(jwtToken);
                userRepository.findByUsername(username).ifPresent(user -> {
                    if (user.isEnabled()) {
                        List<SimpleGrantedAuthority> authorities = user.getAuthorities();
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // Final line to set the authentication to security context
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                });
            } catch (ExpiredJwtException expiredJwtException) {
                throw new ExpiredJwtCustomException(expiredJwtException.getMessage());
            } catch (MalformedJwtException malformedJwtException) {
                throw new MalformedJwtCustomException(malformedJwtException.getMessage());
            } catch (JwtException jwtException) {
                throw new JwtCustomException(jwtException.getMessage());
            } catch (Exception e) {
                throw new JwtCustomException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
