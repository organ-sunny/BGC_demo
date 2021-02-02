package com.sunny.entity;

import com.sunny.util.ClassUtil;

import static com.sunny.util.ClassUtil.getGenericSuperclass;

public class ParentEntity<T> {

    public T getVO() {
        @SuppressWarnings("unchecked")
        Class<T> genericSuperclass = (Class<T>) getGenericSuperclass(this.getClass(), 0);
        return ClassUtil.transformation(this, genericSuperclass);
    }

}
