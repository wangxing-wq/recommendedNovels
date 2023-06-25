package com.novels.common.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * @author 王兴
 * @date 2023/6/24 19:09
 */
@Slf4j
class TokenUtilTest {

    @Test
    public void test() throws NoSuchAlgorithmException {
        // 生成 RSA 密钥对
        KeyPair keyPair = generateKeyPair();
        // 获取公钥和私钥字符串
        String privateKeyString = getPrivateKeyString(keyPair.getPrivate());
        String publicKeyString = getPublicKeyString(keyPair.getPublic());
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setJwtId(UUID.randomUUID().toString());
        jwtProperties.setIssuer("王兴");
        jwtProperties.setAudience("author");
        jwtProperties.setSubject("浏览器");
        jwtProperties.setRefreshExpirationTime(Duration.ofDays(7));
        jwtProperties.setExpirationTime(Duration.ofMinutes(30));
        jwtProperties.setNotBefore(Instant.now());
        UserInfo userInfo = new UserInfo();
        userInfo.setId("111");
        userInfo.setUsername("张三");
        TokenUtil tokenUtil = new TokenUtil(jwtProperties,publicKeyString,privateKeyString);
        String accessToken = tokenUtil.create(userInfo);
        String refreshToken = tokenUtil.createRefreshToken(userInfo);
        log.info("accessToken:{}",accessToken);
        log.info("refreshToken:{}",refreshToken);
        // 查看信息
        log.info("userInfo:{}",tokenUtil.userInfo(accessToken));
        // 是否过期
        log.info("is expire:{}",tokenUtil.expire(accessToken));
        // 做过期
        accessToken = tokenUtil.doExpire(accessToken);
        log.info("do expire:{}",accessToken);
        // 是否过期
        log.info("is expire:{}",tokenUtil.expire(accessToken));
        // 续期
        accessToken = tokenUtil.renewal(accessToken);
        log.info("renewal:{}",tokenUtil.expire(accessToken));
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
