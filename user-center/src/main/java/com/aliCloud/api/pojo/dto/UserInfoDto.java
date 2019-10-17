package com.aliCloud.api.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author GoodMan
 * @since 2019-10-15
 */
@ToString
@Getter
@Setter
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 真实姓名
     */
    private String trueName;
}
