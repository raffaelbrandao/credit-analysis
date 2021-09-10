package com.raffaelbrandao.creditanalysis.service.security;

import com.raffaelbrandao.creditanalysis.entity.UserEntity;
import com.raffaelbrandao.creditanalysis.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService implements TokenService {
    private final UserRepository userRepository;
    private final String key;

    @Autowired
    public JwtService(UserRepository userRepository, @Value("${jwt.key}") String key) {
        this.userRepository = userRepository;
        this.key = key;
    }

    @Override
    public void authenticate(HttpServletRequest request) {
        String token = get(request);

        if (isValid(token)) {
            Optional<UserEntity> userEntity = userRepository.findByUsername(getSubject(token));
            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    @Override
    public String create(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer(userEntity.getName())
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private String get(HttpServletRequest request) {
        Optional<String> token = Optional.of(request.getHeader("Authorization"));
        if (token.isPresent() && !token.get().isEmpty() && token.get().startsWith("Bearer ")) {
            return token.get().substring(7);
        }

        return "";
    }

    private boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }
}
