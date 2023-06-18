package com.novels.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.common.domain.dto.TokenDto;
import com.novels.common.enums.SystemConstantEnum;
import com.novels.common.properties.TokenHelper;
import com.novels.user.dao.UserMapper;
import com.novels.user.domain.model.User;
import com.novels.user.domain.vo.LoginVO;
import com.novels.user.enums.CommunicationEnum;
import com.novels.user.service.UserService;
import com.wx.common.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.novels.user.enums.UserConstantEnum.ACCOUNT_OR_PASSWORD_ERROR;

/**
 * @author 王兴
 * @date 2023/5/19 0:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final TokenHelper tokenHelper;

    public UserServiceImpl(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    @Override
    public TokenDto login(LoginVO loginVO) {
        // 根据Enums 类型查询 email | phone
        // 查询不出来,丢出账号或密码错误请重试
        // 校验用户账号
        boolean isPhone = Objects.equals(loginVO.getLoginType(), CommunicationEnum.PHONE);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (isPhone) {
            queryWrapper.eq(User.COL_PHONE,loginVO.getAccount());
        } else {
            queryWrapper.eq(User.COL_EMAIL,loginVO.getAccount());
        }
        User one = getOne(queryWrapper);
        if (Objects.isNull(one) || !Objects.equals(one.getSalt(),loginVO.getPassword())) {
            throw new BizException(ACCOUNT_OR_PASSWORD_ERROR);
        }
        return new TokenDto(tokenHelper.create(one),tokenHelper.createRefreshToken(one));
    }

    @Override
    public TokenDto logout(TokenDto tokenDto) {
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        tokenDto.setAccessToken(tokenHelper.expire(accessToken,User.class));
        tokenDto.setAccessToken(tokenHelper.expire(refreshToken,User.class));
        return tokenDto;
    }

    @Override
    public TokenDto refreshToken(TokenDto tokenDto) {
        // 判断token 是否失效,失效后判断refreshToken是否失效
        boolean accessTokenExpire = tokenHelper.isVerify(tokenDto.getAccessToken());
        boolean refreshTokenExpire = tokenHelper.isVerify(tokenDto.getRefreshToken());
        if (!accessTokenExpire || !refreshTokenExpire) {
            throw new BizException(SystemConstantEnum.TOKEN_EXPIRE);
        }
        if (!tokenHelper.isExpire(tokenDto.getRefreshToken(), User.class)) {
            tokenDto.setAccessToken(tokenHelper.renewal(tokenDto.getRefreshToken(), User.class));
        } else {
            throw new BizException(SystemConstantEnum.TOKEN_EXPIRE);
        }
        // 如果有效则刷新
        // 如果两者都是失效丢出,token无效,请重新登录
        return tokenDto;
    }

    @Override
    public boolean verify(String token) {
        return tokenHelper.isVerify(token);
    }


}
