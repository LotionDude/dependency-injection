package org.example.ioc.utils;

import com.google.common.collect.ImmutableSet;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;

public class ClassUtils {
    public static Set<Class<?>> findAllClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName, Scanners.TypesAnnotated, Scanners.SubTypes);

        return ImmutableSet.copyOf(reflections.getTypesAnnotatedWith(annotation));
    }

    public static Set<Constructor> findConstructorsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(clazz, Scanners.ConstructorsAnnotated);
        Set<Constructor> constructors = reflections.getConstructorsAnnotatedWith(annotation);

        return constructors;
    }


    public static Optional<Constructor> findEmptyConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return Optional.of(constructor);
            }
        }

        return Optional.empty();
    }
}
