package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LogAnnotationProxyWrapper implements ProxyWrapper {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {
        if (!cls.isAnnotationPresent(Log.class)) {
            Method [] methods = cls.getMethods();
            boolean logMethodAnnotationPresent = false;
            for (Method method : methods){
                if (method.isAnnotationPresent(Log.class)) {
                    logMethodAnnotationPresent = true;
                }
            }
            if(!logMethodAnnotationPresent) {
                return obj;
            }
        }

        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    (proxy, method, args)-> {
                        if (!cls.isAnnotationPresent(Log.class)) {
                            Method[] mets = cls.getMethods();
                            boolean logMethodAnnotationPresent = false;
                            for (Method met : mets) {
                                if (met.isAnnotationPresent(Log.class)) {
                                    System.out.printf(
                                            "Calling method: %s. Args: %s\n", met.getName(), Arrays.toString(args));
                                }
                            }
                        } else {
                            System.out.printf(
                                    "Calling method: %s. Args: %s\n", method.getName(), Arrays.toString(args));
                        }
                        return method.invoke(obj, args);
                    }
            );
        }

        return (T) Enhancer.create(
                cls,
                new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                        if (!cls.isAnnotationPresent(Log.class)) {
                            Method [] mets = cls.getMethods();
                            boolean logMethodAnnotationPresent = false;
                            for (Method met : mets) {
                                if (met.isAnnotationPresent(Log.class)) {
                                    System.out.printf(
                                            "Calling methodioo: %s. Args: %s\n", method.getName(), Arrays.toString(args));
                                }
                            }
                        } else {
                            System.out.printf(
                                    "Calling methodsd: %s. Args: %s\n", method.getName(), Arrays.toString(args));
                        }
                        return method.invoke(obj, args);
                    }
                }
        );
    }
}