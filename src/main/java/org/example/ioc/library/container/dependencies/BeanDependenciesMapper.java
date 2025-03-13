package org.example.ioc.library.container.dependencies;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ioc.library.container.annotations.Inject;
import org.example.ioc.library.container.exceptions.ContainerInjectException;
import org.example.ioc.library.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanDependenciesMapper {
    private final Map<Class<?>, BeanDependencies> dependencies;

    public BeanDependenciesMapper(Set<Class<?>> beanClasses) {
        this.dependencies = this.createBeanDependenciesMapper(beanClasses);
    }

    public Map<Class<?>, BeanDependencies> createBeanDependenciesMapper(Set<Class<?>> beanClasses) {
        Map<Class<?>, BeanDependencies> dependenciesCache = new HashMap<>();
        beanClasses.forEach(bean -> this.createBeanDependencies(bean, dependenciesCache));
        return ImmutableMap.copyOf(dependenciesCache);
    }

    private BeanDependencies createBeanDependencies(Class<?> bean, Map<Class<?>, BeanDependencies> cache) {
        if (cache.containsKey(bean)) {
            return cache.get(bean);
        }

        Constructor constructor = this.getConstructor(bean);
        Set<Class<?>> requiredBeanTypes = Set.of(constructor.getParameterTypes());

        Set<BeanDependencies> subBeanDependencies = requiredBeanTypes
                .stream()
                .map(bd -> this.createBeanDependencies(bd, cache))
                .collect(Collectors.toSet());

        cache.put(bean, new BeanDependencies(bean, constructor, subBeanDependencies));
        return cache.get(bean);
    }

    private Constructor getConstructor(Class<?> clazz) {
        Optional<Constructor> emptyConstructor = ClassUtils.findEmptyConstructor(clazz);
        return emptyConstructor.orElseGet(() -> this.getInjectConstructor(clazz));
    }

    private Constructor getInjectConstructor(Class<?> clazz) {
        Set<Constructor> constructors = ClassUtils.findConstructorsWithAnnotation(clazz, Inject.class);

        if (constructors.size() == 1) {
            return constructors.stream().findAny().get();
        } else {
            throw new ContainerInjectException(String.format(
                    "Cannot inject beans into class [%s]! Contains [%s] inject constructors, while exactly [1] is required!",
                    clazz.getName(),
                    constructors.size()
            ));
        }
    }
}
