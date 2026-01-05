package com.seed4j.generator.server.springboot.database.rest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_DATA_REST;

import com.seed4j.generator.server.springboot.database.rest.application.SpringDataRestApplicationService;
import com.seed4j.module.domain.resource.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringDataRestModuleConfiguration {

  @Bean
  Seed4JModuleResource springDataRestModule(SpringDataRestApplicationService springDataRest) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_DATA_REST)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Data", "Add Spring Data REST to expose JPA repositories as REST endpoints")
      .organization(Seed4JModuleOrganization.builder().feature(DATASOURCE).addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "data", "rest")
      .factory(springDataRest::buildModule);
  }
}
