package org.example.ioc.utils.container;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.example.ioc.utils.container.dependencies.BeanDependencies;
import org.example.ioc.utils.container.dependencies.BeanDependenciesMapper;
import org.example.ioc.utils.container.exceptions.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RequiredArgsConstructor
public class ContainerFactory {
    private final BeanDependenciesMapper dependenciesMapper;

    public Container createContainer() {
        Map<Class<?>, Object> cachedBeans = new HashMap<>();

        dependenciesMapper.getDependencies()
                .values()
                .forEach(beanTypes -> this.createBean(beanTypes, cachedBeans));

        return new Container(ImmutableMap.copyOf(cachedBeans));
    }

    public Object createBean(BeanDependencies beanType, Map<Class<?>, Object> cachedBeans) {
        if (cachedBeans.containsKey(beanType.getBeanClass()))
            return cachedBeans.get(beanType.getBeanClass());

        try {
            Constructor constructor = beanType.getConstructor();
            List<Class> constructorTypes = Arrays.asList(constructor.getParameterTypes());

            Object[] beans = beanType.getDependencies()
                    .values().stream()
                    .map(bd -> this.createBean(bd, cachedBeans))
                    .sorted((Comparator.comparing(o -> constructorTypes.indexOf(o.getClass()))))
                    .toArray();

            Object bean = beanType.getConstructor().newInstance(beans);
            cachedBeans.put(bean.getClass(), bean);
            return bean;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeanCreationException("Could not instantiate bean of class [%s]!", e);
        }
    }
}
