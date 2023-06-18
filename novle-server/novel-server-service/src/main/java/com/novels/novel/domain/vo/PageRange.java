package com.novels.novel.domain.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @author 王兴
 * @date 2023/6/11 16:36
 */
@Data
public class PageRange {

    @NotBlank(message = "页码不能为空")
    @Length(min = 1,message = "页码不能为负数")
    private int page;

    @NotBlank(message = "页数不能为空")
    @Length(max = 20,min = 10,message = "页码支持范围为10 ~ 20")
    private int count;

}
