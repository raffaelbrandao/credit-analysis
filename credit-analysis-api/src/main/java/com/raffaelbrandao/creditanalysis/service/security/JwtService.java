package com.raffaelbrandao.creditanalysis.service.security;

import com.raffaelbrandao.creditanalysis.entity.UserEntity;
import com.raffaelbrandao.creditanalysis.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class JwtService implements TokenService {
    private final UserRepository userRepository;
    private final String key;
    private final Long expiration;

    @Autowired
    public JwtService(UserRepository userRepository, @Value("${jwt.key}") String key, @Value("${jwt.expiration}") Long expiration) {
        this.userRepository = userRepository;
        this.key = key;
        this.expiration = expiration;
    }

    @Override
    public void authenticate(HttpServletRequest request) {
        String token = get(request);

        if (isValid(token)) {
            Optional<UserEntity> userEntity = userRepository.findByUsername(getSubject(token));
            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    @Override
    public String create(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        LocalDateTime now = LocalDateTime.now();

        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(now
                        .plusMinutes(expiration)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .claim("privileges", userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes(UTF_8))
                .compact();
    }

    private String get(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return "";
    }

    private boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key.getBytes(UTF_8))
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key.getBytes(UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}