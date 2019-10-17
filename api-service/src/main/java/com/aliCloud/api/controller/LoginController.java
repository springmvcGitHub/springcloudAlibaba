package com.aliCloud.api.controller;

import com.aliCloud.api.pojo.dto.UserInfoDto;
import com.aliCloud.api.pojo.dto.UserLoginDto;
import com.aliCloud.api.provider.IUserCenterProvider;
import com.aliCloud.api.provider.IUserLoginProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private IUserLoginProvider loginProvider;
    @Reference
    private IUserCenterProvider userCenterProvider;

    @GetMapping(value = "login")
    public Map<String,Object> login(@RequestParam String userName, @RequestParam String password) {
        System.out.println("--------------userController1-----------------");
        Map<String,Object> result = new HashMap<>();
        UserLoginDto userDto = loginProvider.getLoginUser(userName, password);
        if (null == userDto) {
            result.put("success",false);
            result.put("msg","用户名密码错误");
            return result;
        }
        long userId = userDto.getId();
        UserInfoDto userInfoDto = userCenterProvider.getUserInfo(userId);
        if (null == userInfoDto) {
            result.put("success",false);
            result.put("msg","用户已登录，但用户信息缺失");
            return result;
        }
        if (StringUtils.isEmpty(userInfoDto.getTrueName())) {
            result.put("success",false);
            result.put("msg","用户已登录，真实姓名未维护");
            return result;
        }
        result.put("success",true);
        result.put("msg",userInfoDto.getTrueName()+"，欢迎登陆！");
        return result;
    }

    @GetMapping(value = "testAuth")
    public String testAuth() {
        return "success";
    }
}
