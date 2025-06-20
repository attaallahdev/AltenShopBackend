package com.alten.productbackend.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class BeanUtilsPatch {

    public static void copyNonNullProperties(Object source, Object target) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        final BeanWrapper wrappedTarget = new BeanWrapperImpl(target);

        String[] nullPropertyNames = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);

        org.springframework.beans.BeanUtils.copyProperties(source, target, nullPropertyNames);
    }
}
