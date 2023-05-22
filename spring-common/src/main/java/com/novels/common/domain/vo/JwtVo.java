package com.novels.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

/**
 * @author 王兴
 * @date 2023/5/22 23:26
 */
@Data
public class JwtVo<T> {

    private String subject;
    private String audience;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant expiresAt;
    private Instant notBefore;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String issuer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant issuedAt;
    private String jwtId;
    private T json;

}
