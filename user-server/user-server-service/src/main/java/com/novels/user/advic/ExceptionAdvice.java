package com.novels.user.advic;

import com.novels.common.bean.Result;
import com.wx.common.exception.BaseException;
import com.wx.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> messageNotReadable(HttpMessageNotReadableException messageNotReadableException) {
        //
        log.info("{}",messageNotReadableException.getMessage());
        return Result.fail();
    }

    @ExceptionHandler(BizException.class)
    public Result<Void> bizException(BizException bizException) {
        // BaseException
        log.error("{}",bizException.getMessage(),bizException);
        return Result.fail(bizException);
    }

    @ExceptionHandler(BaseException.class)
    public Result<Void> baseException(BaseException baseException) {
        // BaseException
        log.info("{}",baseException.getMessage());
        return Result.fail(baseException);
    }

    @ExceptionHandler({Exception.class,Throwable.class})
    public Result<Void> exception(Throwable throwable) {
        return Result.fail(throwable.getMessage());
    }



}
