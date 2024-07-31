package com.coco.bakingbuddy.jwt.filter;

import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 쿠키에서 JWT를 받아옵니다.
        String token = jwtTokenProvider.resolveTokenFromCookie((HttpServletRequest) request,"accessToken");
        log.info(">>>JwtAuthenticationFilter{}", token);
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            log.info("JwtAuthenticationFilter doFilter token={}",token);
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}