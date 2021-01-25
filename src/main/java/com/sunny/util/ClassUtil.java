package com.sunny.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtil {

    /**
     * 获取类上的泛型
     *
     * @param c     类
     * @param index 序号
     * @return 泛型类
     */
    public static Class<?> getGenericSuperclass(Class<?> c, int index) {
        Type type = ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[index];
        return (Class<?>) type;
    }

    /**
     * 公共转换
     * */
    public static <T> T transformation(Object o, Class<T> tClass) {
        try {
            T t = tClass.newInstance();
            List<Field> declaredFields = getAllFields(tClass);

            for (Field field : declaredFields){
                Class<?> aClass = o.getClass();
                Field[] declaredFields1 = aClass.getDeclaredFields();
                for (Field field1: declaredFields1){
                    field1.setAccessible(true);
                    if(field.getName().equals(field1.getName()) && field.getType() == field1.getType()){
                        field.setAccessible(true);
                        field.set(t , field1.get(o));
                    }
                }
            }
            return t;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("转换VO出错：" + e.getMessage());
        }
    }

    /**
     * 获取类的所有字段，包括父类
     * */
    public static List<Field> getAllFields(Class<?> tClass) {
        Field[] declaredFields = tClass.getDeclaredFields();
        List<Field> result = new ArrayList<>(Arrays.asList(declaredFields));

        Class<?> superclass = tClass.getSuperclass();
        while (superclass != null && !superclass.getName().equals("java.lang.Object")) {
            Field[] declaredFields1 = superclass.getDeclaredFields();
            result.addAll(Arrays.asList(declaredFields1));
            superclass = superclass.getSuperclass();
        }

        return result;
    }

}
