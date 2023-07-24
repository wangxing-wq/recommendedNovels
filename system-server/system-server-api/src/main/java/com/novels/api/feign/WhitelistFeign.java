package com.novels.api.feign;

import com.novels.common.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 白名单列表查询
 * @author 王兴
 * @date 2023/7/16 20:09
 */
@FeignClient("system-server")
@RequestMapping("/api/1.0/list")
@RestController
public interface WhitelistFeign {

    /**
     * 查询有效期的白名单列表
     */
    @GetMapping("ip")
    Result<List<String>> ipWhitelist();

    /**
     * 查询有效期的url列表
     */
    @GetMapping("url")
    Result<List<String>> urlWhitelist();

    /**
     * 查询有效期的用户列表
     */
    @GetMapping("userId")
    Result<List<String>> userIdWhitelist();

}
