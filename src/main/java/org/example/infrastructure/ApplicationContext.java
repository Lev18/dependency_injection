package org.example.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;
import org.example.infrastructure.configreader.ObjectConfigReader;

import java.util.HashMap;
import java.util.Map;

// this class capture specific class context, caches singleton objects
// and return object for specific type from objectFactory
public class ApplicationContext {

    @Setter
    private ObjectFactory objectFactory;

    @Getter
    private ObjectConfigReader objectConfigReader;

    private Map<Class<?>, Object> componentCache = new HashMap<>();

    public ApplicationContext(ObjectConfigReader objectConfigReader) {
        this.objectConfigReader = objectConfigReader;
    }

    // this method first gets implClass type
    // checks does singleton cache contains object of that specific class
    // if it doesn't exist then it create an object with help objectFactory.createObject method
    // and put it into singleton cache
    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> cls) {
        Class<? extends T> implClass = objectConfigReader.getImplClass(cls);

        if (componentCache.containsKey(implClass)) {
            return (T) componentCache.get(implClass);
        }

        T object = objectFactory.createObject(implClass);

        if (implClass.isAnnotationPresent(Component.class)) {
            if (implClass.isAnnotationPresent(Scope.class)) {
                ScopeType scopeType = implClass.getAnnotation(Scope.class).value();
                if (scopeType.equals(ScopeType.SINGLETON)) {
                    componentCache.put(implClass, object);
                }
            }
            else {
                componentCache.put(implClass, object);
            }
        }
        return object;
    }
}
