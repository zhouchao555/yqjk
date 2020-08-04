package com.yhsl.common.kit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class BeanMapKit {

    /**
     * 将给定源bean的属性值复制到新的map中。
     * 并且可以单独执行要包含的字段 如果制定字段为null  那就按照全部字段
     *
     * @param source            the source bean
     * @param includeProperties array of property names to include
     * @throws BeansException if the copying failed
     */
    public static Map<String, Object> Bean2Map(Object source, String[] includeProperties) {

        if(Objects.isNull(source)){
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        //得到源类
        Class<?> sourceEditable = source.getClass();
        //得到源类搜索的字段
        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(sourceEditable);
        //对包含字段数组转集合
        List<String> includeList = (includeProperties != null) ? Arrays.asList(includeProperties) : null;

        for (PropertyDescriptor sourcePd : sourcePds) {
            //得到源类中的方法   并判断是否有包含字段  如果有包含字段那么就按照包含字段去赋值
            if (sourcePd.getWriteMethod() != null &&
                    (includeProperties == null || (includeList.contains(sourcePd.getName())))) {
                PropertyDescriptor sourcePdPd = BeanUtils.getPropertyDescriptor(source.getClass(), sourcePd.getName());
                if (sourcePdPd != null && sourcePdPd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePdPd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        //将字段名 和参数写入到结果map中
                        resultMap.put(sourcePd.getName(), value);
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * Map to Bean
     *
     * @param map
     * @param obj
     */
    public static void Map2Bean(Map<String, Object> map, Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                Object value = map.get(field.getName());
                if (value != null && field.getType() == Long.class) {
                    value = Long.parseLong(value.toString());
                }
                field.set(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map to Bean
     *
     * @param map
     * @param clazz
     */
    public static <T> T Map2Bean(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
            Map2Bean(map, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }



}
