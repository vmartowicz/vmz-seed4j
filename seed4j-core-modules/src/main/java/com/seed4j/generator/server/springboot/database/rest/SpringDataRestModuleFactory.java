package com.seed4j.generator.server.springboot.database.rest;

import static com.seed4j.generator.server.springboot.common.domain.SpringBootProperties.SPRING_MAIN;
import static com.seed4j.generator.server.springboot.common.domain.SpringBootProperties.SPRING_TEST;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class SpringDataRestModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/rest");

  private static final String BASE_PATH = "spring.data.rest.base-path";
  private static final String API_PATH = "/api";

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Spring Data REST"), SOURCE.template("spring-data-rest.md"))
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-rest"))
        .addDependency(halExplorerDependency())
        .and()
      .files()
        .add(
          SOURCE.template("RestConfiguration.java"),
          toSrcMainJava().append(properties.packagePath()).append("technical/infrastructure/config/RestConfiguration.java")
        )
        .and()
      .applicationProperties(SPRING_MAIN)
        .set(propertyKey(BASE_PATH), propertyValue(API_PATH))
        .set(propertyKey("spring.data.rest.detection-strategy"), propertyValue("annotated"))
        .set(propertyKey("spring.data.rest.default-page-size"), propertyValue(20))
        .set(propertyKey("spring.data.rest.max-page-size"), propertyValue(100))
        .and()
      .applicationProperties(SPRING_TEST)
        .set(propertyKey(BASE_PATH), propertyValue(API_PATH))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency halExplorerDependency() {
    return JavaDependency.builder()
      .groupId("org.springframework.data")
      .artifactId("spring-data-rest-hal-explorer")
      .scope(JavaDependencyScope.RUNTIME)
      .build();
  }
}
