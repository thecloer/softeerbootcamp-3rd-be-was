package util;

import annotation.Column;
import model.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class ModelConverter {

    public static <T extends Model> T convert(String stringifiedJson, Class<T> clazz) {
        return convert(JSON.parse(stringifiedJson), clazz);
    }

    public static <T extends Model> T convert(Map<String, String> keyValuePairs, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T model = constructor.newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Column.class))
                    continue;

                Column column = field.getAnnotation(Column.class);
                String key = column.value();

                if (key == null || !keyValuePairs.containsKey(key))
                    continue;

                field.setAccessible(true);
                field.set(model, keyValuePairs.get(key));
            }

            return model;
        } catch (Exception e) {
            // TODO: InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
            e.printStackTrace();
            throw new RuntimeException("모델 객체 생성에 실패했습니다. (" + e.getMessage() + ")");
        }
    }
}
