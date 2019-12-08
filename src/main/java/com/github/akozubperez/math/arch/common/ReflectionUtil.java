package com.github.akozubperez.math.arch.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

public enum ReflectionUtil {
    ; // without instances

    public static Optional<Method> getImplementation(Method method, Class<?> targetClass) {
        try {
            return Optional.of(targetClass.getMethod(method.getName(), method.getParameterTypes()));
        } catch (NoSuchMethodException | SecurityException e) {
            return Optional.empty();
        }
    }
    
    public static <T extends Annotation> Optional<T> getAnnotation(Method method, Class<?> targetClass, Class<T> annotation){
        Optional<Method> opt = getImplementation(method, targetClass);
        T annotationObj = null;
        if (opt.isPresent()){
            annotationObj = opt.get().getAnnotation(annotation);
        }
        if (annotationObj == null){
            annotationObj = method.getAnnotation(annotation);
        }
        return (annotationObj == null) ? Optional.empty() : Optional.of(annotationObj);
    }
}
