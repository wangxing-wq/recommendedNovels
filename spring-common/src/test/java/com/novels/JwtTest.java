package com.novels;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.ClaimsHolder;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.junit.Test;

import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 王兴
 * @date 2023/5/21 13:06
 */
public class JwtTest {

    Algorithm algorithm = Algorithm.HMAC256(generateKey());

    private static final String SECRET_KEY = "my-secret-key";
    private static final String ISSUER = "my-issuer";
    private static final long EXPIRATION_TIME = 3600 * 1000; // 1 hour

    public static String createToken(String subject) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + EXPIRATION_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expirationTime)
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    @Test
    public void createToken() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String jwt = JWT.create().withHeader(header).withIssuer("王兴").sign(algorithm);
        DecodedJWT verify = JWT.require(algorithm).build().verify(jwt);
        System.out.println(verify.getClaims());
    }

    @Test
    public void verifyToken() {

    }


    private static final int KEY_LENGTH = 256;

    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[KEY_LENGTH];
        random.nextBytes(key);
        return key;
    }

}
