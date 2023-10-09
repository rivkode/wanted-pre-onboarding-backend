package com.example.wantedpreonboardingbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.base-package}")
    private String API_BASE_PACKAGE;

    @Value("${swagger.path}")
    private String API_PATH;

    @Value("${swagger.version}")
    private String API_VERSION;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(API_BASE_PACKAGE))
                .paths(PathSelectors.ant(API_PATH))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wanted pre onboarding backend API Test with Swagger")
                .description("Pre onboarding Swagger")
                .version(API_VERSION)
                .build();
    }
}
