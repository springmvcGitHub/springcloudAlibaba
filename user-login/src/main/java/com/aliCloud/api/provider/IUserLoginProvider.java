package com.aliCloud.api.provider;

import com.aliCloud.api.pojo.dto.UserLoginDto;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
public interface IUserLoginProvider {

    UserLoginDto getLoginUser(String userName, String pwd);
}
