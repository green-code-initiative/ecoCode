package io.ecocode.rules.php;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhpRulesSpecificationsRepositoryTest {
  @Test
  void testName() {
    assertThat(PhpRulesSpecificationsRepository.NAME).isEqualTo("ecoCode");
  }

  @Test
  void testRepositoryKey() {
    assertThat(PhpRulesSpecificationsRepository.REPOSITORY_KEY).isEqualTo("ecocode-php");
  }

  @Test
  void testLanguage() {
    assertThat(PhpRulesSpecificationsRepository.LANGUAGE).isEqualTo("php");
  }

  @Test
  void testResourceBasePath() {
    assertThat(PhpRulesSpecificationsRepository.RESOURCE_BASE_PATH).isEqualTo("io/ecocode/rules/php");
  }
}
