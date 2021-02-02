package com.sunny.dto;

import com.sunny.exception.ParamErrorException;

import java.lang.reflect.Field;

import static com.sunny.util.ClassUtil.getGenericSuperclass;

public class ParentDTO<T> {

    public T getEntity(){
        try {
            @SuppressWarnings("unchecked")
            Class<T> genericSuperclass = (Class<T>) getGenericSuperclass(this.getClass(), 0);
            Field[] declaredFields = genericSuperclass.getDeclaredFields();
            T t = genericSuperclass.newInstance();
            for (Field field : declaredFields){
                Class<?> aClass = this.getClass();
                Field[] declaredFields1 = aClass.getDeclaredFields();
                for (Field field1: declaredFields1){
                    field1.setAccessible(true);
                    if(field.getName().equals(field1.getName()) && field.getType() == field1.getType()){
                        field.setAccessible(true);
                        field.set(t , field1.get(this));
                    }
                }
            }
            return t;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 校验
     * */
    public void check() {
        try {
            // 遍历所有字段，找到字段上面的@NotNull注解
            // 如果有这个注解，并且字段值也是空，则抛出参数异常错误
            Field[] declaredFields = this.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(NotNull.class)) {
                    // 字段值
                    Object o = field.get(this);
                    if (o == null) {
                        throw new ParamErrorException();
                    }
                    if (o.toString().equals("")) {
                        throw new ParamErrorException();
                    }
                }
            }
        } catch (java.lang.IllegalAccessException e) {
            throw new RuntimeException("没有访问权限！");
        }
    }

}
