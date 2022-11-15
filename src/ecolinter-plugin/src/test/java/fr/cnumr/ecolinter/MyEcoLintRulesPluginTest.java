package fr.cnumr.ecolinter;

import org.junit.jupiter.api.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.PluginContextImpl;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

class MyEcoLintRulesPluginTest {
    @Test
    void test_plugin_extensions() {
        SonarRuntime runtime = SonarRuntimeImpl.forSonarQube(Version.create(8, 6), SonarQubeSide.SCANNER, SonarEdition.SONARCLOUD);
        Plugin.Context context = new PluginContextImpl.Builder().setSonarRuntime(runtime).build();

        MyEcoLintRulesPlugin underTest = new MyEcoLintRulesPlugin();
        underTest.define(context);

        assertThat(context.getExtensions()).hasSize(2);
    }
}
