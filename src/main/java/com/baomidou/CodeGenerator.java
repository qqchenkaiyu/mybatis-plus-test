package com.baomidou;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

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
        // 雪花算法主键
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSource.getJdbcUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(dataSource.getDriverClassName());
        dsc.setUsername(dataSource.getUsername());
        dsc.setPassword(dataSource.getPassword());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("blog");
        pc.setParent("com.baomidou.ant");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        strategy.setInclude("students");
        mpg.setStrategy(strategy);
        mpg.execute();
        log.info("结束生成代码");
    }

}
