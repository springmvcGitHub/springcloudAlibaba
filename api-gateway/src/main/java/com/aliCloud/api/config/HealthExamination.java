package com.aliCloud.api.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Author: GoodMan
 * Date: 2019/10/17
 * Description:
 */
@Component
@Slf4j
public class HealthExamination implements IPing {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public boolean isAlive(Server server) {
        String url = "http://" + server.getId() + "/heath";
        try {
            ResponseEntity<String> heath = restTemplate.getForEntity(url, String.class);
            if (heath.getStatusCode() == HttpStatus.OK) {
                log.info("ping " + url + " success and response is " + heath.getBody());
                return true;
            }
            log.info("ping " + url + " error and response is " + heath.getBody());
            return false;
        } catch (Exception e) {
            log.info("ping " + url + " failed");
            return false;
        }
    }
}
