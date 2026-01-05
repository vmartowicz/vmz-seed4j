package com.seed4j.generator.server.springboot.database.rest.application;

import com.seed4j.generator.server.springboot.database.rest.domain.SpringDataRestModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringDataRestApplicationService {

  private final SpringDataRestModuleFactory factory;

  public SpringDataRestApplicationService() {
    factory = new SpringDataRestModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
