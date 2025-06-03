//package com.baomidou;
//
//import com.alibaba.nacos.api.NacosFactory;
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.sql.SQLException;
//import java.util.Properties;
//
//@Component
//@Slf4j
//public class NacosConfigQueryExample {
//
//    @Autowired
//    private HikariDataSource dataSource;
//
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
//
//}
