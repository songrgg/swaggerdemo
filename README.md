## Main goal
Maintain the swagger documentation by [Swagger Editor](https://editor.swagger.io) and then you can use the yaml files to generate online swagger documentation easily with [Spring boot](https://spring.io/projects/spring-boot).

## Workflow for Swagger documentation
1. Update swagger documentation with Swagger Editor, export the yaml files
2. Update the yaml files in Spring boot project
3. Redeploy the Spring boot project

## How to setup in Spring boot?
Swagger provides swagger-ui and some jars to host a documentation, you can use Java annotations or yaml files to autogenerate the swagger documentation. The example below is using static yaml files to generate documentation.

**src/main/resources/static/swagger-apis/api1/swagger.yaml**
The static yaml file is fetched from Swagger Editor, put it under the resources directory.
```yaml
swagger: "2.0"
info:
  description: "This is a sample server Petstore server.  You can find out more about     Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).      For this sample, you can use the api key `special-key` to test the authorization     filters."
  version: "1.0.0"
  title: "Swagger Petstore"
  termsOfService: "http://swagger.io/terms/"
  contact:
    email: "apiteam@swagger.io"
  license:
    name: "Apache 2.0"
    url: "http://www.apache.org/licenses/LICENSE-2.0.html"
...
```

**Application.java**
```java
@EnableSwagger2
@SpringBootApplication
public class SwaggerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerDemoApplication.class, args);
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
```

**SwaggerSpecConfig.java**
Read the static yaml files:
`src/main/resources/swagger-apis/api1/swagger.yaml` and `src/main/resources/swagger-apis/api2/swagger.yaml`.
```java
@Configuration
public class SwaggerSpecConfig {

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(
        InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            Arrays.asList("api1", "api2")
                .forEach(resourceName -> resources.add(loadResource(resourceName)));
            return resources;
        };
    }

    private SwaggerResource loadResource(String resource) {
        SwaggerResource wsResource = new SwaggerResource();
        wsResource.setName(resource);
        wsResource.setSwaggerVersion("2.0");
        wsResource.setLocation("/swagger-apis/" + resource + "/swagger.yaml");
        return wsResource;
    }
}
```

**pom.xml**
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-common</artifactId>
    <version>2.9.2</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
```

Run the spring boot server and access `<hostname>/swagger-ui.html` to see the documentation.
