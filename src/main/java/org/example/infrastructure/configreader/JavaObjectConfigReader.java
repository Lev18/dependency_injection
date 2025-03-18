package org.example.infrastructure.configreader;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Qualifier;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

// Create reflection for scanning
public class JavaObjectConfigReader implements ObjectConfigReader {

    private final Reflections reflections;

    public JavaObjectConfigReader(String packageToScan) {
        this.reflections = new Reflections(packageToScan);
    }

    // method checks is cls is interface
    // if it is then it checks implementation of that interface
    // if interface don't have multiple implementations then it returns that specific type
    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getImplClass(Class<T> cls) {

            //TODO: add component annotation checking
            if (!cls.isAnnotationPresent(Component.class)) {
                throw new ClassUnmanagable(cls + " in not being  managed by the framework.\n" +
                        " Please add @Component annotation for further use");
            }
            if (!cls.isInterface()) {
                return cls;
            }
            Set<Class<? extends T>> subTypesOf =
                    reflections.getSubTypesOf(cls);

            if (subTypesOf.size() != 1) {
                throw new RuntimeException("Interface should have only one implementation or you should provide class type");
            }

            return subTypesOf.iterator().next();
    }

    @Override
    public <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls) {
        return reflections.getSubTypesOf(cls);
    }
}
