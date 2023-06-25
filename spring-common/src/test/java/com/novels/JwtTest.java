package com.novels;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公私密钥
 * 要求功能
 * server: 加密,解密,续约,
 * client: 解密,校验
 *
 * @author 王兴
 * @date 2023/5/21 13:06
 */
public class JwtTest {


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

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 生成 RSA 密钥对
        KeyPair keyPair = generateKeyPair();

        // 获取公钥和私钥字符串
        String privateKeyString = getPrivateKeyString(keyPair.getPrivate());
        String publicKeyString = getPublicKeyString(keyPair.getPublic());
        Algorithm algorithm1 = Algorithm.RSA256(null, (RSAPrivateKey) getPrivateKey(privateKeyString));
        Algorithm algorithm2 = Algorithm.RSA256((RSAPublicKey) getPublicKey(privateKeyString), null);
        // 加密 JWT
        String token = createJwt(privateKeyString);

        // 解密 JWT
        DecodedJWT decodedJWT = verifyJwt(token, publicKeyString);
        System.out.println("Decoded JWT: " + decodedJWT.getPayload());
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static String getPrivateKeyString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    private static String getPublicKeyString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    private static String createJwt(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) getPrivateKey(privateKeyString));

        // 加密 JWT
        String token = JWT.create()
                .withSubject("user123")
                .sign(algorithm);

        System.out.println("JWT: " + token);
        return token;
    }

    private static DecodedJWT verifyJwt(String token, String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) getPublicKey(publicKeyString), null);

        // 解密 JWT
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        return verifier.verify(token);
    }

    private static PublicKey getPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicKeySpec);
    }

    private static PrivateKey getPrivateKey(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(privateKeySpec);
    }

}
