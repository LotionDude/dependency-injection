package org.example.ioc.library.container;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Container {
    private final Map<Class<?>, Object> beans;

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass) {
        return (T) this.beans.get(beanClass);
    }
}
