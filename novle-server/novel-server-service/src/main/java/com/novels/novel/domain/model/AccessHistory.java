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
 * 访问历史,书单,小说 | 可以用redis做不一定非要这个表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "access_history")
public class AccessHistory {
    /**
     * 主键
     */
    @TableId(value = "access_history_id", type = IdType.INPUT)
    private Integer accessHistoryId;

    /**
     * 创建人id
     */
    @TableField(value = "created_by_id")
    private Integer createdById;

    /**
     * 访问记录名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 访问的时间秒级
     */
    @TableField(value = "last_time")
    private Integer lastTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    public static final String COL_ACCESS_HISTORY_ID = "access_history_id";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_NAME = "name";

    public static final String COL_LAST_TIME = "last_time";

    public static final String COL_CREATE_TIME = "create_time";
}