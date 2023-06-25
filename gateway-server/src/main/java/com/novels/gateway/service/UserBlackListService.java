package com.novels.gateway.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户的黑白名单识
 *
 * @author 王兴
 * @date 2023/6/24 9:43
 */
@Service
public class UserBlackListService {

    private final List<String> blackListArrayList = Lists.newCopyOnWriteArrayList();
    private final List<String> whiteListArrayList = Lists.newCopyOnWriteArrayList();

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
}
