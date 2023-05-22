package com.novels.user.domain.vo;

import com.novels.user.enums.CommunicationEnum;
import com.novels.user.validate.PhoneGroup;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 登录是校验的用户
 * @author 王兴
 * @date 2023/5/20 12:35
 */
@Data
public class LoginVO {


    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "邮箱规范")
    @Pattern(regexp = "^1(3\\d|4[579]|5[^4\\D]|6[67]|7[^249\\D]|8\\d|9[89])\\d{8}$",message = "手机号不符合规范",groups = {PhoneGroup.class})
    private String account;


    @NotBlank(message = "密码不能为空",groups = Email.class)
    private String password;

    @NotBlank(message = "验证码不能为空",groups = {PhoneGroup.class})
    private String verificationCode;

    @NotNull(message = "通讯类型不能为空,请指定")
    private CommunicationEnum loginType;


}
