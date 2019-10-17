package com.aliCloud.api.pojo.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("user_login")
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 登录密码(暂不做MD5加密)
     */
    @TableField("user_password")
    private String userPassword;
}
