package com.novels.novel.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author 王兴
 * @date 2023/6/11 15:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CarouselAdminVO extends CarouselVO{


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


}
