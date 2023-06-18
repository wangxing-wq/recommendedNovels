package com.novels.novel.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 王兴
 * @date 2023/6/3 21:27
 */
@Data
public class CarouselVO implements Serializable {

    private static final long serialVersionUID = 1L;

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


}
