package com.baomidou.ant.blog;

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
 * @Description Swagger配置文件
 * @Author zaomianbao
 * @Date 2019/1/9
 **/
//该类依赖了google的guava组件和springfox.documentation组件
@Configuration
@EnableSwagger2
//包扫描，在此包下的Controler都会被纳入swagger接口文档生成的范围，这里也可以配置类扫描，同时也可以在这个配置类里面细化过滤规则
public class SpringfoxSwagger2Config {
    @Bean
    public Docket swaggerSpringMvcPlugin(){
                        //指定规范，这里是SWAGGER_2
        return new Docket(DocumentationType.SWAGGER_2)
                //设定Api文档头信息，这个信息会展示在文档UI的头部位置
                .apiInfo(swaggerDemoApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.baomidou"))
                  .build();
    }

    /**
     * 自定义API文档基本信息实体
     * @return
     */
    private ApiInfo swaggerDemoApiInfo(){
        //构建联系实体，在UI界面会显示
        Contact contact = new Contact("枣面包", "http://www.zaomianbao.com", "zaomianbao@163.com");
        return new ApiInfoBuilder()
                    .contact(contact)
                    .title("Swagger2构建RESTful API文档")
                    .description("RESTful API文档")
                    .version("1.0.0")
                    .build();
    }

}