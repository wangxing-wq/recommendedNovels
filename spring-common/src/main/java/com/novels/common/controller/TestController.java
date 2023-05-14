package com.novels.common.controller;

import com.novels.common.annotation.LogHelper;
import com.wx.common.core.bean.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来测试的controller
 * @author 王兴
 * @date 2023/5/14 20:57
 */
@LogHelper(isPretty = true)
@RestController
public class TestController {

    @GetMapping("/test")
    public Result<Void> ok(String name,String sex) {
        return Result.success("name",name,"sex",sex);
    }

}
