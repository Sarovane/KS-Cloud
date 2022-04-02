package com.ihealthink.ks.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xiaoyang
 * @description: swagger配置类
 * @date Created in 13:32 2022/4/1
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiInfo()).
                select().apis(RequestHandlerSelectors.
                //指定controller包
                        basePackage("com.ihealthink.ks.system.webapi"))
                // 选择器选择此包下的所用controller
                .paths(PathSelectors.any())
                .build();
    }

    //swagger2 所需对象
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统管理服务")
                //.contact(new Contact("Sarovane", "http://localhost:80888/hello", "xxx@163.com"))
                .description("系统管理API")
                .version("1.0.0")
                .build();

    }

}
