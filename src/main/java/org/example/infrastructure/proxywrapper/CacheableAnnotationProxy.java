package org.example.infrastructure.proxywrapper;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;
import org.example.infrastructure.configurator.ObjectConfigurator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheableAnnotationProxy implements ProxyWrapper {
   private final static Map<String, Object> localCache = new HashMap<>();

    @Override
    public <T> T wrap(T obj, Class<T> cls) {

        Method[] methods = cls.getMethods();
        boolean isCacheAnnotationPresent = false;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Cacheable.class)) {
                isCacheAnnotationPresent = true;
            }
        }

        if (isCacheAnnotationPresent == false) {
            return obj;
        }

        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    (proxy, method, args)-> {
                        Parameter [] parameters = method.getParameters();
                        String key = "";
                        for (Parameter parameter : parameters) {
                            if (parameter.isAnnotationPresent(CacheKey.class)) {
                                key = parameter.getName().toString() + Arrays.toString(args);
                                if (localCache.containsKey(key)) {
                                    return localCache.get(key);
                                }
                                Object result = method.invoke(obj,args);
                                if (result != null) {
                                    localCache.put(key, result);
                                }
                                return result;
                            }
                        }
                        return method.invoke(obj, args);
                    }
            );
        }
        return  (T) Enhancer.create(
                cls,
                (net.sf.cglib.proxy.InvocationHandler)(o, method, args)-> {
                    Parameter [] parameters = method.getParameters();
                    String key = "";
                    for (Parameter parameter : parameters) {
                        if (parameter.isAnnotationPresent(CacheKey.class)) {
                            key = parameter.getName().toString() + Arrays.toString(args);
                            if (localCache.containsKey(key)) {
                                return localCache.get(key);
                            }
                            Object result = method.invoke(obj,args);
                            if (result != null) {
                                localCache.put(key, result);
                            }
                            return result;
                        }
                    }
                    return method.invoke(obj, args);
                }
        );
    }
}
