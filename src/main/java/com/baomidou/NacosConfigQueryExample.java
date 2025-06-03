package com.baomidou;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NacosConfigQueryExample {

    @Resource
    private RestTemplate restTemplate1;
    @Autowired
    private HikariDataSource dataSource;
    @Autowired
    private DiscoveryClient discoveryClient;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public  void startGen() throws SQLException {
//        // Nacos 服务器地址
//        String serverAddr = "localhost:8848";
//        // 数据 ID（配置文件名）
//        String dataId = "mybatis-plus-test.yml";
//        // 分组（默认 DEFAULT_GROUP）
//        String group = "DEFAULT_GROUP";
//
//        try {
//            // 创建 ConfigService 实例
//            Properties properties = new Properties();
//            properties.put("serverAddr", serverAddr);
//            properties.put("namespace", "public");
//
//            ConfigService configService = NacosFactory.createConfigService(serverAddr);
//            // 查询配置（带超时时间，单位：毫秒）
//            String config = configService.getConfig(dataId, group, 1000);
//
//            if (config != null) {
//                System.out.println("查询到的配置内容：");
//                System.out.println(config);
//            } else {
//                System.out.println("未找到配置：" + dataId);
//            }
//        } catch (NacosException e) {
//            System.err.println("查询配置失败：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }


//    @EventListener(ApplicationReadyEvent.class)
//    public  void startDiscover() throws SQLException {
//        System.out.println("Registered services: " + discoveryClient.getServices());
//        System.out.println("RestTemplate class: " + restTemplate1.getClass());
//
//        // 调用你的服务（假设你的服务有一个 `/hello` 接口）
//        String result = restTemplate1.getForObject(
//                "http://mybatis-plus-test/hello",  // 使用服务名调用（Nacos 负载均衡）
//                String.class
//        );
//        System.out.println("Response from mybatis-plus-test: " + result);
//    }

}
