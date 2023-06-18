package com.novels.novel.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */

/**
 * 书单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "bookshelf")
public class Bookshelf {
    /**
     * 书单列表
     */
    @TableId(value = "bookshelf_id", type = IdType.INPUT)
    private Integer bookshelfId;

    /**
     * 书单名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 简介
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 创建人id
     */
    @TableField(value = "created_by_id")
    private Integer createdById;

    /**
     * 创建人名称
     */
    @TableField(value = "created_by_name")
    private String createdByName;

    /**
     * 状态1: 正常,2,废除,3审核,4:违规
     */
    @TableField(value = "`state`")
    private String state;

    /**
     * 违规理由
     */
    @TableField(value = "reason_for_violation")
    private String reasonForViolation;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public static final String COL_BOOKSHELF_ID = "bookshelf_id";

    public static final String COL_NAME = "name";

    public static final String COL_INTRODUCE = "introduce";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATED_BY_NAME = "created_by_name";

    public static final String COL_STATE = "state";

    public static final String COL_REASON_FOR_VIOLATION = "reason_for_violation";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}