package com.ctgu.bookstore.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Length(min = 3, max = 15, message = "密码长度在6-25")
    private String userPassword;

    @NotBlank(message = "验证码不能为空")
    private String verCode;
}
