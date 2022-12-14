package com.insta.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {
        "com.insta.controller"
})
public class SwaggerConfig {

    public static final String REFERENCE = "Authorization";

    @Bean
    public Docket setUpRestAPI() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Collections.singletonList(setUpSecurityContext()))
                .securitySchemes(List.of(setUpApiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.insta.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(this.setUpApiInfo());
    }

    private SecurityContext setUpSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(setUpDefaultAuth())
                .build();
    }

    private List<SecurityReference> setUpDefaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(REFERENCE, authorizationScopes));
    }

    private ApiKey setUpApiKey() {
        return new ApiKey(REFERENCE, REFERENCE, "header");
    }

    private ApiInfo setUpApiInfo() {
        return new ApiInfoBuilder()
                .title("A companion animal SNS Spring Boot REST API")
                .description("???????????? SNS ???????????? swagger api ?????????.")
                .version("1.0.0")
                .build();
    }
}
