package com.novels.system.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兴
 * @date 2023/7/16 19:42
 */
/**
    * 白名单表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "whitelist")
public class Whitelist {
    /**
     * 白名单ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 白名单类型1:url,2:type,3:uid
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 白名单值
     */
    @TableField(value = "`value`")
    private String value;

    /**
     * 白名单描述或备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 白名单生效时间
     */
    @TableField(value = "effective_at")
    private LocalDate effectiveAt;

    /**
     * 白名单失效时间
     */
    @TableField(value = "expires_at")
    private LocalDate expiresAt;

    /**
     * 记录创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 记录更新时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    public static final String COL_ID = "id";

    public static final String COL_TYPE = "type";

    public static final String COL_VALUE = "value";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_EFFECTIVE_AT = "effective_at";

    public static final String COL_EXPIRES_AT = "expires_at";

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_UPDATED_AT = "updated_at";
}
