package com.novels.common.properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;

/**
 * @author 王兴
 * @date 2023/6/24 14:48
 */
@Slf4j
@Component
public class TokenHelper {

    /**
     * JWT 存放请求头你名称
     */
    public static final String AUTHORIZATION = "Authorization";

    private final JwtProperties jwtProperties;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;



    private static final int KEY_LENGTH = 256;


    public TokenHelper(JwtProperties jwtProperties) {
        try {
            if (StringUtils.hasLength(jwtProperties.getPublicKeyString())) {
                publicKey = (RSAPublicKey) getPublicKey(jwtProperties.getPublicKeyString());
            } else {
                log.warn("no public key config");
            }
            if (StringUtils.hasLength(jwtProperties.getPrivateKeyString())) {
                privateKey = (RSAPrivateKey) getPrivateKey(jwtProperties.getPrivateKeyString());
            } else {
                log.warn("no private key config");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        this.jwtProperties = jwtProperties;
    }

    public String create(UserInfo userInfo) {
        return builderToken(userInfo, Instant.now().plus(jwtProperties.getExpirationTime()));
    }

    public String createRefreshToken(UserInfo userInfo) {
        return builderToken(userInfo,Instant.now().plus(jwtProperties.getRefreshExpirationTime()));
    }


    /**
     * 校验令牌token
     * @param token jwt token
     * @return true 允许,false 不通过
     */
    public boolean verify(String token) {
        return Objects.nonNull(decodeJwt(token));
    }

    /**
     * 是否过期
     * @param token 令牌
     * @return true 过期,false 没过期
     */
    public boolean expire(String token) {
        DecodedJWT decodedJwt = decodeJwt(token);
        if (Objects.isNull(decodedJwt)) {
            return true;
        }
        return decodedJwt.getExpiresAtAsInstant().compareTo(Instant.now()) < 0;
    }

    /**
     * 将这个token 变成过期的
     * @param token 要过期的Token
     * @return 变成过期的Token
     */
    public String doExpire(String token) {
        // 将这个token变成过期的
        return builderToken(userInfo(token).orElse(new UserInfo()),Instant.now());
    }

    /**2023-06-01T13:07:01Z  2023-05-25T13:11:05Z
     * 支持accessToken续期,续期时间为配置时间,默认3minute
     * @param token refreshToken
     * @return 续期后的accessToken
     */
    public String renewal(String token) {
        return renewal(token,false);
    }

    /**
     * 续约token
     * @param token 要刷新的token
     * @param flag 刷新的是否是refreshToken
     * @return 续约后的token,续约时间为当前时间 + 配置的
     */
    public String renewal(String token,boolean flag){
        Optional<UserInfo> userInfo = userInfo(token);
        UserInfo ele = userInfo.orElse(new UserInfo());
        if (flag) {
            return createRefreshToken(ele);
        }
        return create(ele);
    }

    public Optional<UserInfo> userInfo(String token) {
        DecodedJWT decodedJWT = decodeJwt(token);
        if (Objects.isNull(decodedJWT)) {
            return Optional.empty();
        }
        // 验证token 解析内容失败的丢出通用的校验,TokenNoIllegal
        UserInfo userInfo = new UserInfo();
        userInfo.setId(decodedJWT.getClaim(UserInfo.USER_ID).asString());
        userInfo.setUsername(decodedJWT.getClaim(UserInfo.USER_NAME).asString());
        return Optional.of(userInfo);
    }


    public DecodedJWT decodeJwt(String token) {
        // 验证token 解析内容失败的丢出通用的校验,TokenNoIllegal
        DecodedJWT verify;
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        try {
            verify = JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            log.error("解析token失败 {}",token);
            return null;
        }
        return verify;
    }

    private String builderToken(UserInfo userInfo, Instant expiresAt) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "RSA");
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header)
                .withSubject(jwtProperties.getSubject())
                .withAudience(jwtProperties.getAudience())
                .withExpiresAt(expiresAt)
                .withNotBefore(jwtProperties.getNotBefore())
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(Instant.now())
                .withJWTId(jwtProperties.getJwtId())
                .withPayload(userInfo.toPayload())
                .sign(Algorithm.RSA256(null, privateKey));
    }


    /**
     * 根据公钥字符串获取私钥对象
     * @param publicKeyString 公钥字符串
     * @return 私钥对象
     */
    private static PublicKey getPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 根据私钥字符串获取私钥对象
     * @param privateKeyString 私钥字符串
     * @return 私钥对象
     */
    private static PrivateKey getPrivateKey(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(privateKeySpec);
    }


}
