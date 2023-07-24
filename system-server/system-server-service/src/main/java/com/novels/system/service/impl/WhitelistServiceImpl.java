package com.novels.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.system.dao.WhitelistMapper;
import com.novels.system.domain.model.Whitelist;
import com.novels.api.enums.WhitelistTypeEnum;
import com.novels.system.service.WhitelistService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 王兴
 * @date 2023/7/16 19:42
 */
@Service
public class WhitelistServiceImpl extends ServiceImpl<WhitelistMapper, Whitelist> implements WhitelistService {

    @Override
    public List<String> ipWhitelist() {
        return findByValue(WhitelistTypeEnum.IP);
    }

    @Override
    public List<String> urlWhitelist() {
        return findByValue(WhitelistTypeEnum.URL);
    }

    @Override
    public List<String> userIdWhitelist() {
        return findByValue(WhitelistTypeEnum.USER);
    }

    /**
     * 根据白名单类型查询有效期内的白名单那
     * @param type 白名单类型
     * @return 白名单值
     */
    private List<String> findByValue(WhitelistTypeEnum type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("type must be not null");
        }
        QueryWrapper<Whitelist> query = new QueryWrapper<>();
        LocalDate now = LocalDate.now();
        LambdaQueryWrapper<Whitelist> le = query.lambda().select(Whitelist::getValue)
                .eq(Whitelist::getType, type.constant())
                .le(Whitelist::getEffectiveAt, now)
                .ge(Whitelist::getExpiresAt, now);
        List<Whitelist> list = this.list(le);
        return list.stream().map(Whitelist::getValue).collect(Collectors.toList());
    }

}
