package org.example.ioc.library.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ioc.library.container.annotations.Component;
import org.example.ioc.library.container.dependencies.BeanDependenciesMapper;
import org.example.ioc.library.utils.ClassUtils;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ContainerApplication {
    public static Container start(Class<?> clazz) {
        Set<Class<?>> beanTypes = ClassUtils.findAllClassesWithAnnotation(clazz.getPackageName(), Component.class);

        BeanDependenciesMapper mapper = new BeanDependenciesMapper(beanTypes);
        ContainerFactory factory = new ContainerFactory(mapper);

        return factory.createContainer();
    }
}
