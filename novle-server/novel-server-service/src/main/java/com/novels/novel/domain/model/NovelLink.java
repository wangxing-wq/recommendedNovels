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
 * 小说链接
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "novel_link")
public class NovelLink {
    /**
     * 小说链接id
     */
    @TableId(value = "novel_link_id", type = IdType.INPUT)
    private Integer novelLinkId;

    /**
     * 小说id
     */
    @TableField(value = "novel_id")
    private Integer novelId;

    /**
     * 小说链接
     */
    @TableField(value = "link")
    private String link;

    /**
     * 1:正常2:废除3:违规
     */
    @TableField(value = "`state`")
    private String state;

    /**
     * 违规理由,如没有违规,内容和状态值一样
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

    public static final String COL_NOVEL_LINK_ID = "novel_link_id";

    public static final String COL_NOVEL_ID = "novel_id";

    public static final String COL_LINK = "link";

    public static final String COL_STATE = "state";

    public static final String COL_REASON_FOR_VIOLATION = "reason_for_violation";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}