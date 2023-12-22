package com.sparta.plusweek.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.plusweek.global.dto.response.RootResponseDto;
import com.sparta.plusweek.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(request);

        if(StringUtils.hasText(tokenValue)){
            if(!jwtUtil.validateToken(tokenValue)){
                log.error("Token Error");
                // 토큰이 유효하지 않다!!!
                ObjectMapper ob = new ObjectMapper();
                String message = "토큰이 유효하지 않습니다.";
                response.setStatus(400);

                RootResponseDto<Object> rootResponseDto = RootResponseDto.builder()
                    .code(String.valueOf(response.getStatus()))
                    .message(message).build();

                String json = ob.writeValueAsString(rootResponseDto);
                PrintWriter writer = response.getWriter();
                writer.println(json);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e){
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }
}
