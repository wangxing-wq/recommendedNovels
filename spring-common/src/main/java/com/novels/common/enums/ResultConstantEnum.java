package com.novels.common.enums;


import com.wx.common.core.bean.Constant;

/**
 * @author 王兴
 * @date 2023/5/11 0:06
 */
public enum ResultConstantEnum implements Constant {

    ENV("env","存储当前环境的值"),
    CODE("code","存储code的常量key"),
    MESSAGE("message","存储数据信息的常量Key"),
    DETAIL_MESSAGE("detailMessage","详细错误数据"),
    DATA("data","存储数据的常量key"),
    ;

    /**
     * 常量值
     */
    private final String constant;

    /**
     * 常量描述
     */
    private final String desc;

    ResultConstantEnum(String constant, String desc){
        this.constant = constant;
        this.desc = desc;
    }

    @Override
    public String constant() {
        return this.constant;
    }

    @Override
    public String desc() {
        return this.desc;
    }

}
