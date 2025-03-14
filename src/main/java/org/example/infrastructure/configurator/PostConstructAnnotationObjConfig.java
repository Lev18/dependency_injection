package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.PostConstruct;

import java.lang.reflect.Method;

public class PostConstructAnnotationObjConfig implements ObjectConfigurator{
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Method[] allMethods = obj.getClass().getDeclaredMethods();
        for (Method method :allMethods) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(obj);
            }
        }
    }
}
