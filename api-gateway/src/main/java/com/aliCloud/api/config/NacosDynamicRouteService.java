package com.aliCloud.api.config;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * Author: GoodMan
 * Date: 2019/10/18
 * Description:
 */
public interface NacosDynamicRouteService {

    /**
     * 更新路由信息
     */
    String update(RouteDefinition gatewayDefine);
}
