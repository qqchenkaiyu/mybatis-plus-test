package com.baomidou.common;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 文件描述信息
 *
 * @author chenkaiyu
 * @version 1.0
 * @date 2025/6/3 13:17
 */

@Configuration
public class CommonConfig {

    @Bean
    @LoadBalanced  // 启用负载均衡（基于 Nacos 的服务名调用）
    public RestTemplate restTemplate1() {
        return new RestTemplate();
    }
}
