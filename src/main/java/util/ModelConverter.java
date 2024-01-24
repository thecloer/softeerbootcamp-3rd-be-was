package util;

import annotation.Column;
import exception.InternalServerErrorException;
import model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class ModelConverter {

    private static final Logger logger = LoggerFactory.getLogger(ModelConverter.class);

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
            logger.debug("모델 객체 생성에 실패했습니다. ({})", e.getMessage());
            throw new InternalServerErrorException("서버에서 오류가 발생했습니다. 죄송합니다. ");
        }
    }
}
