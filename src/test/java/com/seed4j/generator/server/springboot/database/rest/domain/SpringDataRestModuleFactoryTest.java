package com.seed4j.generator.server.springboot.database.rest.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringDataRestModuleFactoryTest {

  private static final SpringDataRestModuleFactory factory = new SpringDataRestModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.company.myapp")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      //.hasPrefixedFiles("documentation", "spring-data-rest.md")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-rest</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-data-rest-hal-explorer</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/java/com/company/myapp/technical/infrastructure/config/RestConfiguration.java")
      .containing("class RestConfiguration implements RepositoryRestConfigurer")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.data.rest.base-path=/api")
      .containing("spring.data.rest.detection-strategy=annotated")
      .containing("spring.data.rest.default-page-size=20")
      .containing("spring.data.rest.max-page-size=100");
  }
}
