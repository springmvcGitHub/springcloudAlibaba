package com.aliCloud.api.provider;

import com.aliCloud.api.pojo.dto.UserInfoDto;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
public interface IUserCenterProvider {

    UserInfoDto getUserInfo(long userId);
}
