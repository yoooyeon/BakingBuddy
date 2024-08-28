package com.coco.bakingbuddy.jwt.provider;

import com.coco.bakingbuddy.auth.service.PrincipalService;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.jwt.domain.RefreshToken;
import com.coco.bakingbuddy.jwt.repository.RefreshTokenRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.RoleType;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessTokenValidityInMilliseconds; // 1h
    @Value("${jwt.refresh-expiration}")
    private long refreshTokenValidityInMilliseconds; // 30days

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private final RefreshTokenRepository refreshTokenRepository;
    private final PrincipalService principalService;

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = principalService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // Access Token 생성
    public String createAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        RoleType role = user.getRole();

        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenValidityInMilliseconds); // validityInMilliseconds 확인
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Refresh Token으로 Access Token 생성
    public String createAccessTokenFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(refreshToken)
                .getBody();

        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);
        return generateAccessToken(username, roles);
    }

    private String generateAccessToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);

            Claims body = claims.getBody();
            Date expiration = body.getExpiration();
            Date now = new Date();
            return !expiration.before(now);
        } catch (ExpiredJwtException e) {
            log.error("JWT token has expired", e);
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
            throw new CustomException(ErrorCode.JWT_INVALID);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
            throw new CustomException(ErrorCode.JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            log.error("JWT token is empty", e);
            throw new CustomException(ErrorCode.JWT_NO_VALID);
        } catch (Exception e) {
            log.error("An unexpected error occurred while validating the token", e);
            throw new CustomException(ErrorCode.JWT_NO_VALID);
        }
    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {
        RefreshToken token = refreshTokenRepository.findByUsername(username);
        if (token != null) {
            return token.getToken();
        }
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUsername(username);
        tokenEntity.setValid(true);
        refreshTokenRepository.save(tokenEntity);

        return refreshToken;
    }

    // Refresh Token 검증
    public boolean validateRefreshToken(String refreshToken) {
        log.info(">>>validateRefreshToken");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(refreshToken);
            log.info(">>>claims.getBody().getSubject()={}", claims.getBody().getSubject());
            // Refresh Token이 데이터베이스에 저장된 것인지 확인
            RefreshToken tokenEntity = refreshTokenRepository.findByUsername(claims.getBody().getSubject());
            log.info(">>>tokenEntity={}", tokenEntity);
            return tokenEntity != null && tokenEntity.getToken().equals(refreshToken) && tokenEntity.isValid();
        } catch (JwtException e) {
            log.error("JWT Exception: ", e);
            return false;
        } catch (Exception e) {
            log.error("General Exception: ", e);
            return false;
        }
    }

    // 쿠키에 토큰 저장
    public void addTokenToCookie(HttpServletResponse response, String token, String tokenName) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenName.equals("accessToken") ? accessTokenValidityInMilliseconds : refreshTokenValidityInMilliseconds) / 1000);
//        response.addHeader("Access-Control-Allow-Credentials","true");
//        response.addHeader("Access-Control-Allow-Origin","*");
        response.addCookie(cookie);

    }

    // 토큰을 쿠키에서 가져오기
    public String resolveTokenFromCookie(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}