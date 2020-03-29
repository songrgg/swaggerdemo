package org.songrong.swaggerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@SpringBootApplication
public class SwaggerdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerdemoApplication.class, args);
    }

    @Bean
    public Docket swagger() {
        return new Docket(SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }

}
