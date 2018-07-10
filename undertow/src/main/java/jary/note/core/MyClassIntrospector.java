package jary.note.core;

import io.undertow.servlet.api.ClassIntrospecter;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.util.ConstructorInstanceFactory;

public class MyClassIntrospector implements ClassIntrospecter {
    public static final MyClassIntrospector INSTANCE = new MyClassIntrospector();
    @Override
    public <T> InstanceFactory<T> createInstanceFactory(Class<T> aClass) throws NoSuchMethodException {
        return new ConstructorInstanceFactory<>(aClass.getDeclaredConstructor());
    }
}
