package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Env;

import java.lang.reflect.Field;


public class EnvAnnotationObjConfig implements ObjectConfigurator{
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Env.class)) {
                String envVarName = field.getAnnotation(Env.class).value();
                if (!envVarName.equals("") && envVarName != null) {
                    envVarName = field.getName();
                }
                String envVarValue = System.getenv(envVarName);
                if (envVarValue != null) {
                    field.setAccessible(true);
                    field.set(obj, envVarValue);
                }
            }
        }
    }
}
