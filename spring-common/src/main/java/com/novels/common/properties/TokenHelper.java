package com.novels.common.properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novels.common.domain.vo.JwtVo;
import com.novels.common.enums.SystemConstantEnum;
import com.wx.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Registered Claims（注册声明）:
 * JWT（JSON Web Tokens）定义了一些标准的 Registered Claim Names（注册声明名称），用于在 JWT 中传输有关实体（主题）的信息。下面是所有的 Registered Claim Names：
 * "iss"（Issuer）：JWT 的发行者。
 * "sub"（Subject）：JWT 的主题，即 JWT 所代表的实体。
 * "aud"（Audience）：JWT 的受众，即预期接收 JWT 的一方。
 * "exp"（Expiration Time）：JWT 的过期时间，表示 JWT 失效的时间。
 * "nbf"（Not Before）：JWT 的生效时间，表示 JWT 在此时间之前无法使用。
 * "iat"（Issued At）：JWT 的签发时间，表示 JWT 的创建时间。
 * "jti"（JWT ID）：JWT 的唯一标识符，用于避免重复使用 JWT。
 * 其中，前三项（"iss"、"sub"、"aud"）是 JWT 的基本信息，用于标识 JWT 的发行者、主题和受众。后四项（"exp"、"nbf"、"iat"、"jti"）是 JWT 的可选信息，用于控制JWT 的使用和唯一标识 JWT。
 * 除了 Registered Claim Names，JWT 还支持自定义声明，即可以在 JWT 中添加其他的声明，用于传递其他相关信息。自定义声明的名称应该避免与 Registered Claim Names 冲突，并且应该使用唯一的命名空间来避免与其他声明冲突。
 * Public Claims（公共声明）:
 * 任何名称的自定义声明，用于传递公共信息。例如："name"、"email"、"role"、"scope" 等。
 * Private Claims（私有声明）:
 * 任何名称以 "_" 开头的自定义声明，用于传递私有信息。例如："user_id"、"_internal"、"_debug" 等。
 * 需要注意的是，JWT 规范中只定义了 Registered Claims，而 Public Claims 和 Private Claims 是可选的，可以根据需要自定义。在使用自定义声明时，应该避免与 Registered Claims 冲突，并且应该使用唯一的命名空间来避免与其他声明冲突。
 * jwt token 生成工具
 *
 * @author 王兴
 * @date 2023/5/21 14:05
 */
@Slf4j
@Component
public class TokenHelper {

    private final ObjectMapper objectMapper;

    private final JwtProperties jwtProperties;


    Algorithm algorithm = Algorithm.HMAC256(generateKey());

    private static final int KEY_LENGTH = 256;

    public TokenHelper(ObjectMapper objectMapper, JwtProperties jwtProperties) {
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public String create(Object element) {
        return builderToken(element,jwtProperties.getExpirationTime());
    }
    public String createRefreshToken(Object element) {
        return builderToken(element,jwtProperties.getExpirationTime());
    }
    private String builderToken(Object element, TemporalAmount expirationTime) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header)
                .withSubject(jwtProperties.getSubject())
                .withAudience(jwtProperties.getAudience())
                .withExpiresAt(Instant.now().plus(expirationTime))
                .withNotBefore(jwtProperties.getNotBefore())
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(Instant.now())
                .withJWTId(jwtProperties.getJwtId())
                .withClaim("json", toJson(element))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        // 验证token 解析内容失败的丢出通用的校验,TokenNoIllegal
        DecodedJWT verify = null;
        try {
            verify = JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new BizException(SystemConstantEnum.TOKEN_INVALID,e);
        }
        return verify;
    }

    public boolean expire(String token) {
        return !Objects.nonNull(verify(token));
    }

    /**
     * 支持accessToken续期
     * @param token refreshToken
     * @return 续期后的accessToken
     */
    public <T>String renewal(String token, Class<T> tClass) {
        T t = decodeJwt(verify(token),tClass);
        return create(t);
    }

    public <T> T decodeJwt(DecodedJWT token, Class<T> tClass) {
        // 验证token 解析内容失败的丢出通用的校验,TokenNoIllegal
        try {
            String string = token.getClaim("json").asString();
            return objectMapper.readValue(string,tClass);
        } catch (JWTDecodeException | JsonProcessingException e) {
            // 处理解码异常
            throw new BizException(SystemConstantEnum.TOKEN_INVALID,e);
        }
    }

    public <T> JwtVo<T> decodeJwt(String token,Class<T> tClass) {
        JwtVo<T> jwtVo = new JwtVo<>();
        DecodedJWT verify = verify(token);
        jwtVo.setSubject(verify.getSubject());
        jwtVo.setAudience(toJson(verify.getAudience()));
        jwtVo.setExpiresAt(verify.getExpiresAtAsInstant());
        jwtVo.setNotBefore(verify.getNotBeforeAsInstant());
        jwtVo.setIssuer(verify.getIssuer());
        jwtVo.setIssuedAt(verify.getIssuedAtAsInstant());
        jwtVo.setJwtId(verify.getId());
        jwtVo.setJson(decodeJwt(verify,tClass));
        return jwtVo;
    }

    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[KEY_LENGTH];
        random.nextBytes(key);
        return key;
    }

}
