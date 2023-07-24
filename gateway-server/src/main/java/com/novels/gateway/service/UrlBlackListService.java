package com.novels.gateway.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.packagescan.resource.AntPathMatcher;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.novels.common.bean.Result;
import com.novels.api.feign.WhitelistFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * URL 黑白名单识别
 *
 * @author 王兴
 * @date 2023/6/24 9:43
 */
@Slf4j
@Service
public class UrlBlackListService implements CommandLineRunner {

    private final List<String> blackListArrayList = Lists.newCopyOnWriteArrayList();
    private final List<String> whiteListArrayList = Lists.newCopyOnWriteArrayList();

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final WhitelistFeign whitelistFeign;

    public UrlBlackListService(WhitelistFeign whitelistFeign) {
        this.whitelistFeign = whitelistFeign;
    }

    /**
     * 校验这个urls,是否全部符合黑名单
     * @param urls 需要校验的URL
     * @return OK
     */
    public boolean isBlackList(String urls) {
        return existsInTheList(urls, blackListArrayList);
    }

    /**
     * 校验这个urls,是否全部符合白名单
     * @param urls 需要校验的URL
     * @return OK
     */
    public boolean isWhiteList(String urls) {
        return existsInTheList(urls, whiteListArrayList);
    }

    private boolean existsInTheList(String urls, List<String> stringList) {
        if (StrUtil.isBlank(urls)) {
            return false;
        }
        List<String> split = StrUtil.split(urls, StrUtil.C_COMMA);
        // 此处只取缓存池查询,刷新缓存池的任务交给定时任务完成,默认最多缓存1024个服务
        for (String url : split) {
            // 校验这个URL是否是OK的
            if (!stringList.contains(url) && !isMatch(url,stringList)) {
                return false;
            }
        }
        return true;
    }

    private boolean isMatch(String url, List<String> stringList) {
        for (String match : stringList) {
            if (pathMatcher.match(match,url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        // 初始化
    }

    public synchronized void refresh() {
        Result<List<String>> whiteDtoResult = whitelistFeign.urlWhitelist();
        // 收集白名单URL
        if (whiteDtoResult.isSuccess()) {
            whiteListArrayList.clear();
            whiteListArrayList.addAll(whiteDtoResult.data());
        } else {
            log.error("Failed to refresh white url");
        }
    }

}
