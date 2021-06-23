package com.chards.committee.vo;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginVO {
    @NotNull(message = "登陆账号不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码Id不能为空")
    private String captchaId;   // 验证码id
    @NotNull(message = "验证码不能为空")
    private String captcha;     // 对应验证码
}
