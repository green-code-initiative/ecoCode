package io.ecocode.rules.java;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JavaRulesSpecificationsRepositoryTest {
  @Test
  void testName() {
    assertThat(JavaRulesSpecificationsRepository.NAME).isEqualTo("ecoCode");
  }

  @Test
  void testRepositoryKey() {
    assertThat(JavaRulesSpecificationsRepository.REPOSITORY_KEY).isEqualTo("ecocode-java");
  }

  @Test
  void testLanguage() {
    assertThat(JavaRulesSpecificationsRepository.LANGUAGE).isEqualTo("java");
  }

  @Test
  void testResourceBasePath() {
    assertThat(JavaRulesSpecificationsRepository.RESOURCE_BASE_PATH).isEqualTo("io/ecocode/rules/java");
  }
}
