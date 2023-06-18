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
 * 作者信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "author")
public class Author {
    /**
     * 作者
     */
    @TableId(value = "author_id", type = IdType.INPUT)
    private Integer authorId;

    /**
     * 作者名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 小说ids
     */
    @TableField(value = "novels_ids")
    private String novelsIds;

    /**
     * 创建id
     */
    @TableField(value = "created_by_id")
    private String createdById;

    /**
     * 创建名称
     */
    @TableField(value = "created_by_name")
    private String createdByName;

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

    public static final String COL_AUTHOR_ID = "author_id";

    public static final String COL_NAME = "name";

    public static final String COL_INTRODUCE = "introduce";

    public static final String COL_NOVELS_IDS = "novels_ids";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATED_BY_NAME = "created_by_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}