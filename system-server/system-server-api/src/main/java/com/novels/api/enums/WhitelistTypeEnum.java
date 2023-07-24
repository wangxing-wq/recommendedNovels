package com.novels.api.enums;

import com.wx.common.core.bean.Constant;
import lombok.AllArgsConstructor;

/**
 * @author 王兴
 * @date 2023/7/18 0:01
 */
@AllArgsConstructor
public enum WhitelistTypeEnum implements Constant {


    URL("1","白名单,url类型,支持ant模式"),
    IP("2","白名单,ip类型"),
    USER("3","白名单,根据用户id识别"),
    ;

    private final String constant;
    private final String desc;

    @Override
    public String constant() {
        return this.constant;
    }

    @Override
    public String desc() {
        return this.desc;
    }
}
