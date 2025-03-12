package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import lombok.val;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ProperyAnnotationObjectConfig implements ObjectConfigurator {
    private Map<String, String> propertiesMap;

    @SneakyThrows
    public ProperyAnnotationObjectConfig() {
        URL path = ClassLoader.getSystemClassLoader().getResource("application.properties");
        Stream<String> lines = new BufferedReader(new FileReader(path.getPath())).lines();
        propertiesMap = lines.map(line->line.split("=")).collect(toMap(arr->arr[0], arr->arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                // if properties name is not provided then it takes field name
                // and field values set from application.properties file
                String value = property.value().isEmpty() ?
                        propertiesMap.get(field.getName()) :
                        propertiesMap.get(property.value());
                field.setAccessible(true);
                field.set(obj, value);
            }
        }
    }
}
