package com.alkemy.ong.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        .securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey()))
        .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
  }

  /**
   * Provides information about the API: name, terms of service, description, contact information,
   * version.
   * 
   * @return an API Info object
   */
  private ApiInfo apiInfo() {
    return new ApiInfo("OT133-Server", "Alkemy 2022", "v1", "urn:tos", null, "MIT LICENSE",
        "https://mit-license.org/", Collections.emptyList());
  }

  /**
   * Provides the authorization description.
   * 
   * @return an APIKey object
   */
  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  /**
   * Provides a default security reference.
   * 
   * @return a default set of authorizations to apply to each api operation
   */
  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  /**
   * Provides a default security scope.
   * 
   * @return a list of security references
   */
  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

}
