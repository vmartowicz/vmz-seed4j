package com.seed4j.generator.server.springboot.database.rest;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

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
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("documentation", "spring-data-rest.md")
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
              <groupId>org.springframework.data</groupId>
              <artifactId>spring-data-rest-hal-explorer</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/java/com/seed4j/growth/technical/infrastructure/config/RestConfiguration.java")
      .containing("class RestConfiguration implements RepositoryRestConfigurer")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          data:
            rest:
              base-path: /api
              default-page-size: 20
              detection-strategy: annotated
              max-page-size: 100
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          data:
            rest:
              base-path: /api
        """
      );
  }
}
