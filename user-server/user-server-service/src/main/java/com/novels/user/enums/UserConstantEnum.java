package com.novels.user.enums;

import com.wx.common.core.bean.Constant;

/**
 * @author 王兴
 * @date 2023/5/21 3:22
 */
public enum UserConstantEnum implements Constant {


    ACCOUNT_OR_PASSWORD_ERROR("A000001", "账号或密码错误"),
    ;

    private final String constant;
    private final String desc;

    UserConstantEnum(String constant,String desc) {
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
