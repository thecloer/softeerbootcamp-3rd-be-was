package pipeline.responseProcessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pipeline.responseProcessor.templateEngine.TemplateRenderer;
import util.http.ContentType;
import util.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateRendererTest {

    private final TemplateRenderer templateRenderer = new TemplateRenderer();

    @ParameterizedTest
    @MethodSource("templateEngineTestCases")
    @DisplayName("템플릿 엔진은 템플릿의 키({{key}})를 해당 키에 대응되는 값으로 치환한다.")
    void templateEngine_process(String template, Map<String, String> templateData, String expected) {
        // given

        HttpResponse response = new HttpResponse()
                .setContentType(ContentType.HTML)
                .setBody(template);
        templateData.forEach(response::setTemplateData);

        // when
        response = templateRenderer.process(response);

        // then
        assertThat(new String(response.getBody(), StandardCharsets.UTF_8)).isEqualTo(expected);
    }

    private static Stream<Arguments> templateEngineTestCases() {
        return Stream.of(
                Arguments.of("{", new HashMap<>(), "{"),
                Arguments.of("{{", new HashMap<>(), "{{"),
                Arguments.of("{{}", new HashMap<>(), "{{}"),
                Arguments.of("{{}}", new HashMap<>(), ""),
                Arguments.of("{{{}}", new HashMap<>(), ""), // {{ { }} 로 볼 수 있고 키 값이 "{"인 것으로 볼 수 있다.
                Arguments.of("{{{{}}", new HashMap<>(), "{{"), // 키 형식이 중첩될 경우 안쪽을 키로 취급하고 나머지는 문자열로 취급한다. "{{ {{key}}" 로 볼 수 있다.
                Arguments.of("{{}}}}", new HashMap<>(), "}}"), // 키 형식이 중첩될 경우 안쪽을 키로 취급하고 나머지는 문자열로 취급한다. "{{key}} }}" 로 볼 수 있다.
                Arguments.of("{{{{}}}}", new HashMap<>(), "{{}}"), // 키 형식이 중첩될 경우 안쪽을 키로 취급하고 나머지는 문자열로 취급한다. "{{ {{key}} }}" 로 볼 수 있다.
                Arguments.of("{{}}}", new HashMap<>(), "}"), // {{}} 는 빈 문자열을 키값으로 볼 수 있고 마지막의 "}"는 그대로 출력된다.
                Arguments.of("{}}", new HashMap<>(), "{}}"),
                Arguments.of("}}", new HashMap<>(), "}}"),
                Arguments.of("}", new HashMap<>(), "}"),
                Arguments.of("[{{this key is not in template data}}] is empty", new HashMap<>(), "[] is empty"),
                Arguments.of("{{key}}", Map.of("key", "value"), "value"),
                Arguments.of("this is {{key}}", Map.of("key", "value"), "this is value"),
                Arguments.of("{{key}} is this", Map.of("key", "value"), "value is this"),
                Arguments.of("{{key-1}}, {{key-2}}", Map.of("key-1", "value-1", "key-2", "value-2"), "value-1, value-2"),
                Arguments.of("{{key-1}}, {{key-2}}, {{key-3}}", Map.of("key-1", "value-1", "key-2", "value-2", "key-3", "value-3"), "value-1, value-2, value-3"),
                Arguments.of("{{key-1}}, {{key-2}}, {{key-3}}", Map.of("key-1", "value-1", "key-2", "value-2"), "value-1, value-2, "),
                Arguments.of("{{ only open braces", new HashMap<>(), "{{ only open braces")
        );
    }
}