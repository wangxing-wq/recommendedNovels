package com.novels.api.domain.dto;

import lombok.Data;

import javax.sound.sampled.Port;

/**
 * @author 王兴
 * @date 2023/7/16 20:26
 */
@Data
public class WhiteDto {

    private String id;

    /**
     * 白名单值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

}
