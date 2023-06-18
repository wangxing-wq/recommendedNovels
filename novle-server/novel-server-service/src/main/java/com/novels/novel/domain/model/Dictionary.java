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
 * 字典表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`dictionary`")
public class Dictionary {
    /**
     * 字典主键
     */
    @TableId(value = "dict_id", type = IdType.INPUT)
    private Integer dictId;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典码值
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典描述
     */
    @TableField(value = "`desc`")
    private String desc;

    /**
     * 1:正常,2:废除
     */
    @TableField(value = "stata")
    private String stata;

    /**
     * 创建id
     */
    @TableField(value = "create_by_id")
    private Integer createById;

    /**
     * 创建人名称
     */
    @TableField(value = "create_by_name")
    private String createByName;

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

    public static final String COL_DICT_ID = "dict_id";

    public static final String COL_DICT_NAME = "dict_name";

    public static final String COL_DICT_CODE = "dict_code";

    public static final String COL_DESC = "desc";

    public static final String COL_STATA = "stata";

    public static final String COL_CREATE_BY_ID = "create_by_id";

    public static final String COL_CREATE_BY_NAME = "create_by_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}