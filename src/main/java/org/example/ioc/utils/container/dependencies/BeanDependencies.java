package org.example.ioc.utils.container.dependencies;

import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class BeanDependencies {
    private final Class<?> beanClass;
    private final Map<Class<?>, BeanDependencies> dependencies;
    private final Constructor constructor;

    public BeanDependencies(Class<?> beanClass, Constructor constructor, Set<BeanDependencies> dependencies) {
        this.beanClass = beanClass;
        this.constructor = constructor;
        this.dependencies = dependencies
                .stream()
                .collect(Collectors.toMap(BeanDependencies::getBeanClass, beanDependencies -> beanDependencies));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        else if (!(other instanceof BeanDependencies)) return false;

        BeanDependencies otherDependencies = (BeanDependencies) other;
        return this.beanClass.equals(otherDependencies.beanClass);
    }
}
