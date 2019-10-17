package com.aliCloud.api.pojo.vo;

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
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 真实姓名
     */
    private String trueName;
}
