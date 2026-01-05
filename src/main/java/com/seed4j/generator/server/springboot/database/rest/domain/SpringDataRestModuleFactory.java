package com.seed4j.generator.server.springboot.database.rest.domain;

import static com.seed4j.module.domain.Seed4JModule.*;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringDataRestModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/rest");
  private static final String TECHNICAL_INFRASTRUCTURE_CONFIG = "technical/infrastructure/config";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    return moduleBuilder(properties)
      .documentation(documentationTitle("Spring Boot Data Rest"), SOURCE.file("spring-data-rest.md"))
      .javaDependencies()
      .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-rest"))
      .addDependency(
        JavaDependency.builder()
          .groupId("org.springframework.data")
          .artifactId("spring-data-rest-hal-explorer")
          .scope(JavaDependencyScope.RUNTIME)
          .build()
      )
      .and()
      .files()
      .add(
        SOURCE.template("RestConfiguration.java"),
        toSrcMainJava().append(packagePath).append(TECHNICAL_INFRASTRUCTURE_CONFIG).append("RestConfiguration.java")
      )
      .and()
      .springMainProperties()
      .set(propertyKey("spring.data.rest.base-path"), propertyValue("/api"))
      .set(propertyKey("spring.data.rest.detection-strategy"), propertyValue("annotated"))
      .set(propertyKey("spring.data.rest.default-page-size"), propertyValue("20"))
      .set(propertyKey("spring.data.rest.max-page-size"), propertyValue("100"))
      .and()
      .springTestProperties()
      .set(propertyKey("spring.data.rest.base-path"), propertyValue("/api"))
      .and()
      .build();
  }
}
