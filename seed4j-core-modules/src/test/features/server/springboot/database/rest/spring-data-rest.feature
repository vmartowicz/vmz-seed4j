Feature: Spring Data REST

  Scenario: Should add Spring Data REST configuration
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | datasource-postgresql |
      | jpa-postgresql        |
      | spring-boot-data-rest |
    Then I should have files in "src/main/java/com/seed4j/growth/technical/infrastructure/config"
      | RestConfiguration.java |
