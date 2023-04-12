package io.ecocode.rules.java;

public final class JavaRulesSpecificationsRepository {
  public static final String REPOSITORY_KEY = "ecocode-java";
  public static final String NAME = "ecoCode";
  public static final String LANGUAGE = "java";
  public static final String RESOURCE_BASE_PATH = JavaRulesSpecificationsRepository.class.getPackageName().replace('.', '/');

  private JavaRulesSpecificationsRepository() {
  }
}
