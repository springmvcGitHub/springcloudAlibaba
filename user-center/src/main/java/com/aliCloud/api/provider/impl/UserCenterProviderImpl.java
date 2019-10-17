package com.aliCloud.api.provider.impl;

import com.aliCloud.api.pojo.dto.UserInfoDto;
import com.aliCloud.api.pojo.po.UserInfo;
import com.aliCloud.api.provider.IUserCenterProvider;
import com.aliCloud.api.service.IUserInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
@Service(protocol = "dubbo")
public class UserCenterProviderImpl implements IUserCenterProvider {

    @Resource
    private IUserInfoService userLoginService;

    @Override
    public UserInfoDto getUserInfo(long userId) {
        UserInfo user = userLoginService.selectOne(new EntityWrapper<UserInfo>().eq("user_id", userId));
        UserInfoDto userDto = null;
        if (null != user) {
            userDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }
}
