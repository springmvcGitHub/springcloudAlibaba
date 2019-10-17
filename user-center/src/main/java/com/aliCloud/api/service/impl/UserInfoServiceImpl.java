package com.aliCloud.api.service.impl;

import com.aliCloud.api.mapper.UserInfoMapper;
import com.aliCloud.api.pojo.po.UserInfo;
import com.aliCloud.api.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GoodMan
 * @since 2019-10-15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
