package com.novels.common.advice;

import com.novels.common.bean.Result;
import com.novels.common.enums.SystemConstantEnum;
import com.wx.common.exception.BaseException;
import com.wx.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 王兴
 * @date 2023/5/21 3:10
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> messageNotReadable(HttpMessageNotReadableException messageNotReadableException) {
        log.error("server-name:{} fail message: {}",applicationName,messageNotReadableException.getMessage());
        return Result.fail();
    }


    @ExceptionHandler(BizException.class)
    public Result<Void> bizException(BizException bizException) {
        log.error("server-name:{} fail message: {}",applicationName,bizException.getMessage(),bizException);
        return Result.fail(bizException);
    }

    @ExceptionHandler(BaseException.class)
    public Result<Void> baseException(BaseException baseException) {
        log.error("server-name:{} fail message: {}",applicationName,baseException.getMessage());
        return Result.fail(baseException);
    }

    @ExceptionHandler({Exception.class,Throwable.class})
    public Result<Void> exception(Throwable throwable) {
        log.error("server-name:{} Exception :{} ,fail message: {}",applicationName,throwable.getClass(),throwable.getMessage());
        return Result.fail(new BizException(SystemConstantEnum.FAIL,throwable));
    }



}
