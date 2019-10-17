package com.aliCloud.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: GoodMan
 * Date: 2019/10/16
 * Description:
 */
@Controller
public class ToHtmlController {

    @RequestMapping(value = "toIndex")
    public String toLoginPage() {
        return "/index";
    }
}
