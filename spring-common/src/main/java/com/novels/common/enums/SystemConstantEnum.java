package com.novels.common.enums;


import com.wx.common.core.bean.Constant;

/**
 * 参数校验响应结果码
 * @author 22343
 * @version 1.0
 */
public enum SystemConstantEnum implements Constant {


    /**
     * 参数校验异常
     */
    SUCCESS("000000", "成功"),
    FAIL("000001", "失败"),
    PARAM_CHECK("000002", "参数校验异常"),


    /**
     * dao层数据操作异常
     */
    DELETE_EXCEPTION("000003", "删除操作异常"),
    INSERT_EXCEPTION("000004", "插入操作异常"),
    UPDATE_EXCEPTION("000005", "更新操作异常"),
    SELECT_EXCEPTION("000006", "查询操作异常"),
    TOKEN_INVALID("000006", "token无效"),
    TOKEN_EXPIRE("000007", "token失效"),
    ;

    /**
     * 常量值
     */
    private final String constant;
    /**
     * 常量描述
     */
    private final String desc;

    SystemConstantEnum(String constant, String desc) {
        this.constant = constant;
        this.desc = desc;
    }

    public String constant() {
        return this.constant;
    }

    public String desc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return "SystemConstantEnum{" +
                "constant='" + constant + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }


}
