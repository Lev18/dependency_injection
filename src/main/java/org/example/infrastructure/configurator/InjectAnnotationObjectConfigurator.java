package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.Qualifier;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Qualifier.class)) {
                    // TODO move to getObject ApplicationContext
                    Qualifier classQualifier = field.getAnnotation(Qualifier.class);
                    if (field.getType().isAssignableFrom(classQualifier.value())) {
                        field.set(obj, context.getObject(classQualifier.value()));
                    }
                    else {
                        throw  new RuntimeException("@Qualifier is not assignable to " + classQualifier.value());
                    }
                }
                else {
                    field.set(obj, context.getObject(field.getType()));
                }
            }
        }
    }
}
