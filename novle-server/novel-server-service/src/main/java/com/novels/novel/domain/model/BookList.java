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
 * 书架 | 可以配合redis使用,redis定时缓存到表 | 书架小说novles_ids通过缓存实现
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "book_list")
public class BookList {
    /**
     * 书架主键
     */
    @TableId(value = "book_list_id", type = IdType.INPUT)
    private Integer bookListId;

    /**
     * 书架名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 1: 正常 2: 删除  只含这两个状态
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 小说主键通过,分割
     */
    @TableField(value = "novel_ids")
    private String novelIds;

    /**
     * 创建人id
     */
    @TableField(value = "created_by_id")
    private Integer createdById;

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

    public static final String COL_BOOK_LIST_ID = "book_list_id";

    public static final String COL_NAME = "name";

    public static final String COL_STATE = "state";

    public static final String COL_NOVEL_IDS = "novel_ids";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}