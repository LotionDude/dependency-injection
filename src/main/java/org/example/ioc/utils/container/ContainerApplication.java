package org.example.ioc.utils.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ioc.utils.container.annotations.Component;
import org.example.ioc.utils.container.dependencies.BeanDependenciesMapper;
import org.example.ioc.utils.ClassUtils;

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
