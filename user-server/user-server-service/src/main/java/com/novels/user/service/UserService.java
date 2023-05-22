package com.novels.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.novels.common.domain.dto.TokenDto;
import com.novels.user.domain.model.User;
import com.novels.user.domain.vo.LoginVO;

/**
 * @author 王兴
 * @date 2023/5/19 0:45
 */
public interface UserService extends IService<User> {

    /**
     * 成功返回token 否则为空字符串
     *
     * @param loginVO 登录对象
     * @return token
     */
    TokenDto login(LoginVO loginVO);


    TokenDto refreshToken(TokenDto tokenDto);

    boolean expire(String token);
}
