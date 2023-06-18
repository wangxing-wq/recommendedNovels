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

    /**
     * 登出账号
     * @param tokenDto token携带bean
     * @return 已登出
     */
    TokenDto logout(TokenDto tokenDto);

    /**
     * 刷新令牌,续期时间为配置时间,默认30minute
     *
     * @param tokenDto 需要刷新的token
     * @return 刷新后的token
     */
    TokenDto refreshToken(TokenDto tokenDto);

    /**
     * 是否过期
     *
     * @param token 需要验证是否过期的token
     * @return 结果
     */
    boolean verify(String token);


}
