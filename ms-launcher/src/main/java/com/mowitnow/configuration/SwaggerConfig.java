package com.mowitnow.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;

@Configuration
@ConfigurationProperties(prefix = "swagger.doc")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .ignoredParameterTypes(ModelAndView.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Api MowItNow",
                "Api permettant de manipuler une tondeuse automatisée",
                "1.0-SNAPSHOT",
                null,
                new Contact("Cyril Marchive", "https://github.com/langston8182", "cyril.marchive@gmail.com"),
                "",
                "",
                emptyList());
    }
}
