package com.aliCloud.api.controller;

import com.aliCloud.api.pojo.dto.UserInfoDto;
import com.aliCloud.api.pojo.dto.UserLoginDto;
import com.aliCloud.api.provider.IUserCenterProvider;
import com.aliCloud.api.provider.IUserLoginProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@Slf4j
@RefreshScope
public class LoginController {

    //这里的loadbalance如果设置为leastactive，测试的时候数据量不能太小，不然看不出来效果。
    //我是用jmter20秒内发送了2000次请求，节点1(手动sleep300毫秒)：188次，节点2：973次，节点3：911次。
    //而且根据网上的资料，如果loadbalance = "leastactive",权重的配置将会失效。
    //actives表示Activity最大的并发数，如果超过这个并发数，在invoke时将会等待。
    @Reference(loadbalance = "leastactive",retries = 0,filter = "activelimit")
    private IUserLoginProvider loginProvider;
    @Reference
    private IUserCenterProvider userCenterProvider;
    @Value(value = "${testKey:defaultValue}")
    private String testKey;


    @GetMapping(value = "login")
    public Map<String, Object> login(@RequestParam String userName, @RequestParam String password) {
        log.info("--------------userController2-----------------,testKey:" + testKey);
        Map<String, Object> result = new HashMap<>();
        UserLoginDto userDto = loginProvider.getLoginUser(userName, password);
        if (null == userDto) {
            result.put("success", false);
            result.put("msg", "用户名密码错误");
            return result;
        }
        long userId = userDto.getId();
        UserInfoDto userInfoDto = userCenterProvider.getUserInfo(userId);
        if (null == userInfoDto) {
            result.put("success", false);
            result.put("msg", "用户已登录，但用户信息缺失");
            return result;
        }
        if (StringUtils.isEmpty(userInfoDto.getTrueName())) {
            result.put("success", false);
            result.put("msg", "用户已登录，真实姓名未维护");
            return result;
        }
        result.put("success", true);
        result.put("msg", userInfoDto.getTrueName() + "2，欢迎登陆！");
        return result;
    }

    @GetMapping(value = "testAuth")
    public String testAuth() {
        return "success";
    }
}
