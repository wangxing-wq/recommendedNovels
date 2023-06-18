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
 * 公告信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "announcement")
public class Announcement {
    /**
     * 公告主键
     */
    @TableId(value = "announcement_id", type = IdType.INPUT)
    private Integer announcementId;

    /**
     * 公告内容
     */
    @TableField(value = "announcement_content")
    private String announcementContent;

    /**
     * 公告开始时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 公告结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**
     * 顺序
     */
    @TableField(value = "sorted")
    private Integer sorted;

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
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public static final String COL_ANNOUNCEMENT_ID = "announcement_id";

    public static final String COL_ANNOUNCEMENT_CONTENT = "announcement_content";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_SORTED = "sorted";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATED_BY_NAME = "created_by_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}