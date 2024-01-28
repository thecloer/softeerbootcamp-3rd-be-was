package util;

import exception.BadRequestException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JSON {

    static public Map<String, String> parse(String stringifiedJson) {
        Map<String, String> keyValueMap = new HashMap<>();

        stringifiedJson = stringifiedJson.trim();
        if (!isObjectType(stringifiedJson))
            throw new BadRequestException("JSON 형식이 아닙니다. (괄호가 없습니다.)");

        stringifiedJson = peel(stringifiedJson);

        String[] keyValuePairs = stringifiedJson.split(",");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":");
            if (keyValue.length != 2)
                throw new BadRequestException("JSON 형식이 아닙니다. (key:value 쌍이 아닙니다.)");


            String key = keyValue[0].trim();
            if (!isKeyType(key))
                throw new BadRequestException("JSON 형식이 아닙니다. (key 형식이 잘못됐습니다.)");

            key = peel(key);

            String value = keyValue[1].trim();
            if ("null".equals(value))
                value = "";
            else if (isStringType(value)) {
                value = peel(value);
            }
            // TODO: value가 Object일 경우 재귀호출 && value가 Array일 경우 구현 & 재귀호출

            keyValueMap.put(key, value);
        }

        return Collections.unmodifiableMap(keyValueMap);
    }

    static private String peel(String stringifiedJson) {
        return stringifiedJson.substring(1, stringifiedJson.length() - 1);
    }

    static private Boolean isObjectType(String stringifiedJson) {
        return stringifiedJson.startsWith("{") && stringifiedJson.endsWith("}");
    }

    static private Boolean isKeyType(String stringifiedJson) {
        return stringifiedJson.length() > 2 && isStringType(stringifiedJson);
    }

    static private Boolean isStringType(String stringifiedJson) {
        return stringifiedJson.startsWith("\"") && stringifiedJson.endsWith("\"");
    }

    static public String stringify(Map<String, String> object) {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Map.Entry<String, String> entry : object.entrySet()) {
            stringBuilder.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() == null) {
                stringBuilder.append("null,");
                continue;
            }
            stringBuilder.append("\"").append(entry.getValue()).append("\",");
        }
        stringBuilder
                .deleteCharAt(stringBuilder.length() - 1)
                .append("}");

        return stringBuilder.toString();
    }
}
