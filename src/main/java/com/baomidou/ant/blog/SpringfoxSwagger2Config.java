package com.baomidou.ant.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description Swagger配置文件  http://127.0.0.1:8080/swagger-ui.html
 * @Author zaomianbao
 * @Date 2019/1/9
 **/
//该类依赖了google的guava组件和springfox.documentation组件
@Configuration
@EnableSwagger2
@Slf4j
//包扫描，在此包下的Controler都会被纳入swagger接口文档生成的范围，这里也可以配置类扫描，同时也可以在这个配置类里面细化过滤规则
public class SpringfoxSwagger2Config {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        log.info("swagger 信息地址 : {}", "http://127.0.0.1:8080/swagger-ui.html");
        //指定规范，这里是SWAGGER_2
        return new Docket(DocumentationType.SWAGGER_2)
                //设定Api文档头信息，这个信息会展示在文档UI的头部位置
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.baomidou"))
                .build();
    }
}