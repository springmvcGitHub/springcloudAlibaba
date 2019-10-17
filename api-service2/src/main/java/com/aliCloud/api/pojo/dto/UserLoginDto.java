package com.aliCloud.api.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
@ToString
@Getter
@Setter
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 登录密码(暂不做MD5加密)
     */
    private String userPassword;
}