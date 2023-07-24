package com.novels.user.contoroller.web;

import com.novels.common.bean.Result;
import com.novels.common.domain.dto.TokenDto;
import com.novels.common.properties.TokenHelper;
import com.novels.user.constant.ApiVersionConstant;
import com.novels.user.domain.vo.LoginVO;
import com.novels.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Web用户操作
 * @author 王兴
 * @date 2023 /5/19 0:45
 */
@RestController
@RequestMapping(ApiVersionConstant.ONE + "auth")
public class UserWebController {

    private final UserService userService;
    private final TokenHelper tokenHelper;


    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     * @param tokenHelper the token helper
     */
    public UserWebController(UserService userService, TokenHelper tokenHelper) {
        this.userService = userService;
        this.tokenHelper = tokenHelper;
    }

    /**
     * 登录用户,支持邮箱和手机号
     * @param loginVO 登录实体Vo
     * @return the result
     */
    @PostMapping("login")
    public Result<Void> login(@RequestBody LoginVO loginVO) {
        // 成功,返回一个Token,失败返回一个null
        TokenDto token = userService.login(loginVO);
        if (Objects.isNull(token)) {
            // 账号或者密码失败
            return Result.fail();
        }
        return Result.success(token);
    }

    /**
     * 登出
     * @return the result
     */
    @PostMapping("logout")
    public Result<TokenDto> logout(@RequestBody TokenDto tokenDto) {
        return Result.success(userService.logout(tokenDto));
    }

    /**
     * 刷新令牌token
     * @return the result
     */
    @PostMapping("refreshToken")
    public Result<TokenDto> refreshToken(@RequestBody TokenDto tokenDto) {
        // 解析refreshToken,如果未过期,
        return Result.success(userService.refreshToken(tokenDto));
    }

    /**
     * 校验令牌
     * @return the result
     */
    @PostMapping("verify")
    public Result<Void> verify(String token) {
        return Result.success(userService.verify(token));
    }

    @PostMapping("show")
    public Result<String> show(@RequestBody TokenDto tokenDto) {
        return Result.success("AccessToken",tokenHelper.decodeJwt(tokenDto.getAccessToken()),"RefreshToken",tokenHelper.decodeJwt(tokenDto.getRefreshToken()));
    }


}
