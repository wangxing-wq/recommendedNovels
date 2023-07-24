package com.novels.gateway.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.novels.common.bean.Result;
import com.novels.api.feign.WhitelistFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户的黑白名单识
 *
 * @author 王兴
 * @date 2023/6/24 9:43
 */
@Slf4j
@Service
public class UserBlackListService {

    private final List<String> blackListArrayList = Lists.newCopyOnWriteArrayList();
    private final List<String> whiteListArrayList = Lists.newCopyOnWriteArrayList();

    private final WhitelistFeign whitelistFeign;

    public UserBlackListService(WhitelistFeign whitelistFeign) {
        this.whitelistFeign = whitelistFeign;
    }

    public boolean isBlackList(String id) {
        return isExists(id,blackListArrayList);
    }

    public boolean isWhiteList(String id) {
        return isExists(id,whiteListArrayList);
    }

    public boolean isExists(String id, List<String> list) {
        if (StrUtil.isBlank(id) || CollUtil.isEmpty(list)) {
            return false;
        }
        return list.contains(id);
    }

    public synchronized void refresh() {
        Result<List<String>> whiteDtoResult = whitelistFeign.userIdWhitelist();
        // 收集白名单URL
        if (whiteDtoResult.isSuccess()) {
            whiteListArrayList.clear();
            whiteListArrayList.addAll(whiteDtoResult.data());
        } else {
            log.error("Failed to refresh white url");
        }
    }
}
