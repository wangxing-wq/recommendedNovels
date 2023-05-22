package com.novels.user.domain.model;

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
 * @date 2023/5/19 0:28
 */
/**
    * 用户信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_info")
public class UserInfo {
    /**
     * 用户主键
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户头像
     */
    @TableField(value = "user_photo")
    private String userPhoto;

    /**
     * 1:男 2: 女 3:未定义
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 1: 正常 2: 废除 3: 审核(不适用) 4: 违规
     */
    @TableField(value = "`status`")
    private Integer status;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_NICK_NAME = "nick_name";

    public static final String COL_USER_PHOTO = "user_photo";

    public static final String COL_SEX = "sex";

    public static final String COL_STATUS = "status";

    public static final String COL_REASON_FOR_VIOLATION = "reason_for_violation";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}
