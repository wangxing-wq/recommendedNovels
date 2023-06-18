package com.novels.novel.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 轮播表 @TableName,@TableId,@TableField 起别名
 * # 排除非表字段的三种方式
 * 1. 字段静态化 + 加 static
 * 2. 步序列化字段 + 加 transient
 * 3. @TableField 显示属性 exist = false,
 *
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Data
@TableName(value = "carousel")
public class Carousel {

    /**
     * 轮播主键
     */
    @TableId(value = "carousel_id", type = IdType.INPUT)
    @NotBlank(message = "主键不能为空")
    private Integer carouselId;

    /**
     * 活动的展示图片
     */
    @TableField(value = "img_url")
    @NotBlank(message = "轮播展示图片链接不能为空")
    private String imgUrl;

    /**
     * 活动的访问链接
     */
    @TableField(value = "link")
    @NotBlank(message = "轮播活动链接")
    private String link;

    /**
     * 轮播图开始时间
     */
    @TableField(value = "start_time")
    @NotBlank(message = "活动开始时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 轮播图结束时间
     */
    @TableField(value = "end_time")
    @NotBlank(message = "活动结束时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 顺序
     */
    @TableField(value = "sort")
    @NotBlank(message = "顺序不能为空")
    @Min(value = 0,message = "顺序最小粒度为0")
    private Integer sort;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建人名称
     */
    @TableField(value = "create_by_name")
    private String createByName;

    /**
     * 修改时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public Carousel() {
    }

    public Carousel(Integer carouselId, String imgUrl, String link, Integer sort, String createBy, String createByName, LocalDateTime createTime, LocalDateTime updateTime) {
        this.carouselId = carouselId;
        this.imgUrl = imgUrl;
        this.link = link;
        this.sort = sort;
        this.createBy = createBy;
        this.createByName = createByName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static final String COL_CAROUSEL_ID = "carousel_id";

    public static final String COL_IMG_URL = "img_url";

    public static final String COL_LINK = "link";

    public static final String COL_SORT = "sort";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_BY_NAME = "create_by_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}
