package com.atria.userservice.security;

import com.atria.userservice.dto.ErrorResponseDto;
import com.atria.userservice.exception.ExpiredJwtCustomException;
import com.atria.userservice.exception.JwtCustomException;
import com.atria.userservice.exception.MalformedJwtCustomException;
import com.atria.userservice.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        log.info("Authorization Header : "+authorization);
        boolean isBearerToken = Objects.nonNull(authorization) ? authorization.startsWith("Bearer ") : false;
        if (isBearerToken) {
            String jwtToken = authorization.substring(7);

            try {
                if (!jwtService.isAccessToken(jwtToken)) {
                    filterChain.doFilter(request, response);
                    return;
                }
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
                SecurityContextHolder.clearContext();
                sendErrorResponse(response, "JWT token is expired", HttpStatus.UNAUTHORIZED);
                return;
            } catch (MalformedJwtException malformedJwtException) {
                SecurityContextHolder.clearContext();
                sendErrorResponse(response, "Invalid JWT token", HttpStatus.BAD_REQUEST);
                return;
            } catch (JwtException jwtException) {
                SecurityContextHolder.clearContext();
                sendErrorResponse(response, "JWT validation failed", HttpStatus.UNAUTHORIZED);
                return;
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                sendErrorResponse(response, e.getMessage(), HttpStatus.UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/v1/auth");
    }

    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "JWT Authentication Failed : Full Authentication Required to access this resource",
                status,
                message,
                LocalDateTime.now().toString()
        );
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDto));
    }
}
