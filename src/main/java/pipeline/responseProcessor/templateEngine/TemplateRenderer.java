package pipeline.responseProcessor.templateEngine;

import pipeline.responseProcessor.ResponseProcessor;
import util.http.ContentType;
import util.http.HttpResponse;

import java.nio.charset.StandardCharsets;

public class TemplateRenderer implements ResponseProcessor {

    @Override
    public HttpResponse process(HttpResponse response) {
        if (!shouldProcess(response))
            return response;

        char[] body = bytesToChars(response.getBody());
        StringBuilder processedBody = new StringBuilder(response.getBodyLength());
        StringBuilder templateKey = new StringBuilder();

        boolean isKeyOpen = false;
        for (int i = 0; i < body.length; i++) {
            // i번째 문자 부터 탬플릿 키("{{key}}") 가 만들어 질 수 있는 경우 (최소 탬플릿 키는 "{{}}"이므로 i + 3 < body.length)
            if (i + 3 < body.length && body[i] == '{' && body[i + 1] == '{') {
                // 키 형식이 중첩되는 경우 안쪽 키를 우선시 한다. 예를 들어 "{{ {{ }} }}" 의 경우 "{{ {{key}} }}"로 보고 앞의 "{{"와 뒤의 "}}"는 문자열로 취급한다.
                if (isKeyOpen) {
                    processedBody.append("{{").append(templateKey);
                    templateKey.setLength(0);
                } else
                    isKeyOpen = true;
                i++;
            } else if (i + 1 < body.length && body[i] == '}' && body[i + 1] == '}') {
                if (!isKeyOpen) {
                    processedBody.append(body[i]);
                    continue;
                }
                isKeyOpen = false;
                i++;

                String key = templateKey.toString();
                String data = response.getTemplateData(key);
                processedBody.append(data);
                templateKey.setLength(0);
            } else if (isKeyOpen)
                templateKey.append(body[i]);
            else // 탬플릿 키와 관련 없는 문자열은 그대로 출력
                processedBody.append(body[i]);
        }

        // 탬플릿 키가 닫히지 않은 경우 "{{ key" 는 그대로 출력
        if (isKeyOpen)
            processedBody.append("{{").append(templateKey);

        response.setBody(processedBody.toString());
        return response;
    }

    private Boolean shouldProcess(HttpResponse response) {
        return response.getContentType() == ContentType.HTML && response.getBodyLength() > 0;
    }

    private char[] bytesToChars(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8).toCharArray();
    }
}
