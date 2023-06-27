package fr.greencodeinitiative.javascript;

import org.junit.jupiter.api.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarRuntime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class JavaScriptPluginTest {

    @Test
    void extensions() {
        SonarRuntime sonarRuntime = mock(SonarRuntime.class);
        Plugin.Context context = new Plugin.Context(sonarRuntime);
        new JavaScriptPlugin().define(context);
        assertThat(context.getExtensions()).hasSize(1);
    }

}
