package com.novels.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.novels.common.bean.Result;
import com.novels.system.domain.model.Whitelist;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 白名单服务
 * @author 王兴
 * @date 2023/7/16 19:42
 */
public interface WhitelistService extends IService<Whitelist> {

    /**
     * 查询有效期的白名单列表
     */
    List<String> ipWhitelist();

    /**
     * 查询有效期的url列表
     */
    List<String> urlWhitelist();

    /**
     * 查询有效期的用户列表
     */
    List<String> userIdWhitelist();

}
