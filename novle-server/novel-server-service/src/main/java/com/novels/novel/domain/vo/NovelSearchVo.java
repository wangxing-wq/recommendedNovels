package com.novels.novel.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兴
 * @date 2023/5/29 21:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelSearchVo {


    private String name;
    private String type;
    private String author;
    private String keywords;

}
