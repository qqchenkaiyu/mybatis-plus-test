package com.baomidou;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.endpoint.NacosConfigEndpointAutoConfiguration;
import com.alibaba.nacos.api.config.ConfigService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.baomidou.ant.blog.mapper")
@SpringBootApplication //
// 先排除自动配置

public class MybatisPlusTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisPlusTestApplication.class, args);


        // 手动触发 Nacos 配置加载
        NacosConfigManager nacosConfigManager = context.getBean(NacosConfigManager.class);
        ConfigService configService = nacosConfigManager.getConfigService();
        String serverStatus = configService.getServerStatus();
    }

}
