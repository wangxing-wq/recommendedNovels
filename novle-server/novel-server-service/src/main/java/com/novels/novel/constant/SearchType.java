package com.novels.novel.constant;

import com.wx.common.core.bean.Constant;

/**
 * @author 王兴
 * @date 2023/5/29 21:31
 */
public enum SearchType implements Constant {


    NOVEL("novel","根据小说名称搜索"),
    AUTHOR("author","根据小说作者名称搜索"),
    TAG("tag","根据小说标签搜索"),
    ;

    SearchType(String constant,String desc){
        this.constant = constant;
        this.desc = desc;
    }
    private String constant;
    private String desc;

    @Override
    public String constant() {
        return null;
    }

    @Override
    public String desc() {
        return null;
    }
}
