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
 * 小说信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "novel")
public class Novel {
    /**
     * 小说主键
     */
    @TableId(value = "novel_id", type = IdType.INPUT)
    private Integer novelId;

    /**
     * 小说名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 小说内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 小说封面
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 小说字数
     */
    @TableField(value = "word_count")
    private BigDecimal wordCount;

    /**
     * 作者名称(保留后续可能删除)
     */
    @TableField(value = "author_name")
    private String authorName;

    /**
     * 小说作者主键
     */
    @TableField(value = "author_id")
    private Integer authorId;

    /**
     * 分类主键
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 分类名称(保留后续可能删除)
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 小说生命周期,幼苗,连载,完本,太监
     */
    @TableField(value = "novel_life")
    private String novelLife;

    /**
     * 小说状态1. 正常,2.废除,3.审核,4.异常(可补充新的)
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 创建人id
     */
    @TableField(value = "created_by_id")
    private Integer createdById;

    /**
     * 创建人昵称
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

    public static final String COL_NOVEL_ID = "novel_id";

    public static final String COL_NAME = "name";

    public static final String COL_CONTENT = "content";

    public static final String COL_PIC_URL = "pic_url";

    public static final String COL_WORD_COUNT = "word_count";

    public static final String COL_AUTHOR_NAME = "author_name";

    public static final String COL_AUTHOR_ID = "author_id";

    public static final String COL_CATEGORY_ID = "category_id";

    public static final String COL_CATEGORY_NAME = "category_name";

    public static final String COL_NOVEL_LIFE = "novel_life";

    public static final String COL_STATE = "state";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATED_BY_NAME = "created_by_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}