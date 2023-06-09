package io.ecocode.rules.python;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PythonRulesSpecificationsRepositoryTest {
  @Test
  void testName() {
    assertThat(PythonRulesSpecificationsRepository.NAME).isEqualTo("ecoCode");
  }

  @Test
  void testRepositoryKey() {
    assertThat(PythonRulesSpecificationsRepository.REPOSITORY_KEY).isEqualTo("ecocode-python");
  }

  @Test
  void testLanguage() {
    assertThat(PythonRulesSpecificationsRepository.LANGUAGE).isEqualTo("py");
  }

  @Test
  void testResourceBasePath() {
    assertThat(PythonRulesSpecificationsRepository.RESOURCE_BASE_PATH).isEqualTo("io/ecocode/rules/python");
  }
}
