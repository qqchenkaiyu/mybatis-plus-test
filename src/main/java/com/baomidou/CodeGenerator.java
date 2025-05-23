package com.baomidou;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Slf4j
public class CodeGenerator {

    @Autowired
    private HikariDataSource dataSource;


    @EventListener(ApplicationReadyEvent.class)
    public  void startGen() throws SQLException {
        log.info("开始生成代码");
        // 如果ant/blog/controller 文件夹路径下有文件，则不生成
        boolean needStart = false;
        try {
            Class<?> aClass = Class.forName("com.baomidou.ant.blog.controller.StudentsController");
        } catch (ClassNotFoundException e) {
            needStart = true;
        }
        if (!needStart){
            log.info("ant/blog/controller 文件夹路径下有文件，则不生成");
            return;
        }
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("chenkaiyu");
        gc.setParent("com.baomidou.ant.blog");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSource.getJdbcUrl());
        dsc.setDriverName(dataSource.getDriverClassName());
        dsc.setUsername(dataSource.getUsername());
        dsc.setPassword(dataSource.getPassword());
        mpg.setDataSource(dsc);


        mpg.execute();
        log.info("结束生成代码");
    }

}
