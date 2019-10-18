package com.aliCloud.api.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Author: GoodMan
 * Date: 2019/10/18
 * Description:
 */
@Component
@Slf4j
public class NacosListen implements CommandLineRunner {

    //    @NacosInjected
//    private ConfigService configService;
    @Autowired
    private NacosDynamicRouteService nacosDynamicRouteService;
    @Value("${spring.application.name}" + "." + "${spring.cloud.nacos.config.file-extension}")
    private String dataId;
    //    @Value("${spring.cloud.nacos.config.file-extension}")
//    private String fileExtension;
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    private static String group = "DEFAULT_GROUP";

    @Override
    public void run(String... args) throws Exception {
        addRouteNacosListen();
    }

    /**
     * 添加动态路由监听器
     */
    private void addRouteNacosListen() {
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
//            properties.put(PropertyKeyConst.NAMESPACE, "8282c713-da90-486a-8438-2a5a212ef44f");
            ConfigService configService = NacosFactory.createConfigService(properties);

            String configInfo = configService.getConfig(dataId, group, 5000);
            log.info("从Nacos返回的配置：" + configInfo);
            getNacosDataRoutes(configInfo);
            //注册Nacos配置更新监听器，用于监听触发
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("Nacos更新了！");
                    log.info("接收到数据:" + configInfo);
                    getNacosDataRoutes(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });

        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
            e.printStackTrace();
        }
    }

    /**
     * 从Nacos返回的配置
     *
     * @param configInfo
     */
    private void getNacosDataRoutes(String configInfo) {
        List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
        list.stream().forEach(definition -> {
            log.info("" + JSON.toJSONString(definition));
            nacosDynamicRouteService.update(definition);
        });
    }
}
