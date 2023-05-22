package com.novels.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/**
 * "iss"（Issuer）：JWT 的发行者。
 * "sub"（Subject）：JWT 的主题，即 JWT 所代表的实体。
 * "aud"（Audience）：JWT 的受众，即预期接收 JWT 的一方。
 * "exp"（Expiration Time）：JWT 的过期时间，表示 JWT 失效的时间。
 * "nbf"（Not Before）：JWT 的生效时间，表示 JWT 在此时间之前无法使用。
 * "iat"（Issued At）：JWT 的签发时间，表示 JWT 的创建时间。
 * "jti"（JWT ID）：JWT 的唯一标识符，用于避免重复使用 JWT。
 * @author 王兴
 * @date 2023 /5/21 20:02
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * "iss"（Issuer）：JWT 的发行者。
     */
    private String issuer;

    /**
     * "sub"（Subject）：JWT 的主题，即 JWT 所代表的实体。
     */
    private String subject;

    /**
     * "aud"（Audience）：JWT 的受众，即预期接收 JWT 的一方。
     */
    private String audience;

    /**
     * "exp"（Expiration Time）：JWT 的过期时间，表示 JWT 失效的时间。 | 这个是时间单位,默认天
     */
    private Duration expirationTime = Duration.ofMinutes(30);

    /**
     * 维持刷新token的有效性
     */
    private Duration refreshExpirationTime = Duration.ofDays(7);

    /**
     * "nbf"（Not Before）：JWT 的生效时间，表示 JWT 在此时间之前无法使用。
     */
    private Instant notBefore = Instant.now();

    /**
     * "jti"（JWT ID）：JWT 的唯一标识符，用于避免重复使用 JWT。
     */
    private String jwtId;


}
