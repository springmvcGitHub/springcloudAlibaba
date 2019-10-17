package com.aliCloud.api.provider.impl;

import com.aliCloud.api.pojo.dto.UserLoginDto;
import com.aliCloud.api.pojo.po.UserLogin;
import com.aliCloud.api.provider.IUserLoginProvider;
import com.aliCloud.api.service.IUserLoginService;
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
public class UserLoginProviderImpl implements IUserLoginProvider {

    @Resource
    private IUserLoginService userLoginService;

    @Override
    public UserLoginDto getLoginUser(String userName, String userPassword) {
        System.out.println("--------进入节点2----------");
        UserLogin user = userLoginService.selectOne(new EntityWrapper<UserLogin>().eq("user_name", userName).eq("user_password", userPassword));
        UserLoginDto userDto = null;
        if (null != user) {
            userDto = new UserLoginDto();
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }
}
