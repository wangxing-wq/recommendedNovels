package com.novels.novel.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */

/**
 * 评判标准
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "appraise")
public class Appraise {
    /**
     * 评价主键
     */
    @TableId(value = "appraise_id", type = IdType.INPUT)
    private Integer appraiseId;

    /**
     * 评分
     */
    @TableField(value = "score")
    private BigDecimal score;

    /**
     * 推荐指数
     */
    @TableField(value = "recommended")
    private Integer recommended;

    /**
     * 浏览量
     */
    @TableField(value = "views")
    private Integer views;

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

    public static final String COL_APPRAISE_ID = "appraise_id";

    public static final String COL_SCORE = "score";

    public static final String COL_RECOMMENDED = "recommended";

    public static final String COL_VIEWS = "views";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}