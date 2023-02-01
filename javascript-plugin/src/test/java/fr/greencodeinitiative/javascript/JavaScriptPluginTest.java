package fr.greencodeinitiative.javascript;

import org.junit.Test;
import org.sonar.api.*;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaScriptPluginTest {

    @Test
    public void extensions() {
        Plugin.Context context = new Plugin.Context(new MockedSonarRuntime());
        new JavaScriptPlugin().define(context);
        assertThat(context.getExtensions()).hasSize(1);
    }

    private static class MockedSonarRuntime implements SonarRuntime {

        @Override
        public Version getApiVersion() {
            return Version.create(9, 9);
        }

        @Override
        public SonarProduct getProduct() {
            return SonarProduct.SONARQUBE;
        }

        @Override
        public SonarQubeSide getSonarQubeSide() {
            return SonarQubeSide.SCANNER;
        }

        @Override
        public SonarEdition getEdition() {
            return SonarEdition.COMMUNITY;
        }
    }

}
