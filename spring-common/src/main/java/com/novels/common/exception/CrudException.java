package com.novels.common.exception;

import com.wx.common.core.bean.Constant;
import com.wx.common.exception.BizException;

/**
 * @author 王兴
 * @date 2023/5/15 22:11
 */
public class CrudException extends BizException {

    public CrudException(Constant constant) {
        super(constant);
    }

}
