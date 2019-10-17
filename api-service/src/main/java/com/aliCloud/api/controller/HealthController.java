package com.aliCloud.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: GoodMan
 * Date: 2019/10/17
 * Description:
 */
@RestController
public class HealthController {

    /**
     * 用于心跳检测
     */
    @GetMapping(value = "/heath")
    @ResponseBody
    public String heath() {
        return "ok";
    }

}
