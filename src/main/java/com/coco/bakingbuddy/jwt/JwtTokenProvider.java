package com.coco.bakingbuddy.jwt;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.service.PrincipalService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final PrincipalService principalService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessTokenValidityInMilliseconds; // 24h
    @Value("${jwt.expiration}")
    private long validityInMilliseconds; // 24h
    @Value("${jwt.refresh-expiration}")
    private long refreshTokenValidityInMilliseconds;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, String role) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        log.info(">>>>createToken={}", claims);
        Date expirationDate = new Date(now.getTime() + validityInMilliseconds * 1000); // validityInMilliseconds 확인
        log.info(">>>set expirationDate{}", expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = principalService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "Bearer TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info(">>>>bearerToken={}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        log.info(">>>>validateToken2={}", jwtToken);

        try {
            log.info(">>>>validateToken=3{}", jwtToken);
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);

            Claims body = claims.getBody();
            Date expiration = body.getExpiration();
            Date now = new Date();


            // 로그 출력
            log.info(">>>> JWT Token: {}", jwtToken);
            log.info(">>>> Secret Key: {}", secretKey); // secretKey는 로그에 포함되지 않는 것이 좋지만 필요시 추가
            log.info(">>>> Claims: {}", body);
            log.info(">>>> Expiration: {}", expiration);
            log.info(">>>> Current Time: {}", now);

            boolean isNotExpired = !expiration.before(now);
            log.info(">>>> Is Token Expired: {}", !isNotExpired);

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

//    private String generateToken(String username, List<String> roles) {
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("roles", roles);
//
//        Date now = new Date();
//        Date expirationDate = new Date(now.getTime() + validityInMilliseconds);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidityInMilliseconds * 1000))
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
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(refreshToken);

            // Refresh Token이 데이터베이스에 저장된 것인지 확인
            RefreshToken tokenEntity = refreshTokenRepository.findByUsername(claims.getBody().getSubject());
            return tokenEntity != null && tokenEntity.getToken().equals(refreshToken) && tokenEntity.isValid();
        } catch (Exception e) {
            return false;
        }
    }

    // Access Token 생성
    public String createAccessTokenFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(refreshToken)
                .getBody();

        // 기존 Claims에서 사용자 정보 및 권한을 추출
        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);

        return generateAccessToken(username, roles);
    }

    // Access Token 생성
    private String generateAccessToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenValidityInMilliseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}