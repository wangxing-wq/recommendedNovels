package com.novels.user.enums;

import com.wx.common.core.bean.Constant;

/**
 * 通讯类型
 * @author 王兴
 * @date 2023/5/20 20:51
 */
public enum CommunicationEnum implements Constant {

    PHONE("国内手机","手机通讯"),
    EMAIL("邮箱","国内邮箱"),
    ;

    private final String constant;
    private final String desc;

    CommunicationEnum(String constant,String desc) {
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
