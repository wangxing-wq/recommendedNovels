package com.novels.system.controller;

import com.novels.common.annotation.LogHelper;
import com.novels.common.bean.Result;
import com.novels.api.feign.WhitelistFeign;
import com.novels.system.service.WhitelistService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 名单
 * @author 王兴
 * @date 2023/7/17 23:47
 */
@LogHelper(isPretty = true)
@RestController
public class ListController implements WhitelistFeign {

    private final WhitelistService whitelistService;

    public ListController(WhitelistService whitelistService) {
        this.whitelistService = whitelistService;
    }

    @Override
    public Result<List<String>> ipWhitelist() {
        return Result.success(whitelistService.ipWhitelist());
    }

    @Override
    public Result<List<String>> urlWhitelist() {
        return Result.success(whitelistService.urlWhitelist());
    }

    @Override
    public Result<List<String>> userIdWhitelist() {
        return Result.success(whitelistService.userIdWhitelist());
    }

}
