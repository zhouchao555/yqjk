package com.yhsl.common.kit;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Map工具类
 */
public class MapsKit {
    /**
     * 根据value排序
     *
     * @param map
     * @param sort   排序规则 true升序，false降序
     * @param length 返回长度
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean sort, Integer
            length) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (sort) {
                    return (o1.getValue()).compareTo(o2.getValue());
                } else {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            }
        });
        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list.subList(0, length)) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Map key 排序
     *
     * @param map map
     * @return map
     */
    public static Map<String, String> order(Map<String, String> map) {
        HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }

    /**
     * 转换对象为map
     *
     * @param object object
     * @param ignore ignore 忽略的KEY
     * @return map
     */
    public static <T> Map<String, T> objectToMap(Object object, String... ignore) {
        Map<String, T> tempMap = new LinkedHashMap<String, T>();
        List<String> ignores = Lists.newLinkedList();
        if (ignore != null && ignore.length > 0) {
            for (String i : ignore) {
                ignores.add(i);
            }
        }
        for (Field f : getAllFields(object.getClass())) {
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            if (ignores.size() > 0 && ignores.contains(f.getName())) {
                continue;
            } else {
                Object o = null;
                try {
                    o = f.get(object);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                tempMap.put(f.getName(), (T) o);
            }
        }
        tempMap.remove("serialVersionUID");//去除序列化ID
        return tempMap;
    }

    /**
     * map转换为对象
     *
     * @param ignore 字段名称
     * @return map
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz, String... ignore) {
        if (map == null) {
            return clazz;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            List<String> ignores = Lists.newLinkedList();
            if (ignore != null && ignore.length > 0) {
                for (String i : ignore) {
                    ignores.add(i);
                }
            }
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                if (map.containsKey(field.getName())) {
                    boolean ig = false;
                    if (ignores.size() > 0 && ignores.contains(field.getName())) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(obj, map.get(field.getName()));
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 获取所有Fields,包含父类field
     *
     * @param clazz clazz
     * @return list
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        if (!clazz.equals(Object.class)) {
            List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
            List<Field> fields2 = getAllFields(clazz.getSuperclass());
            if (fields2 != null) {
                fields.addAll(fields2);
            }
            return fields;
        } else {
            return null;
        }
    }

    /**
     * url 参数串连
     *
     * @param map            map
     * @param keyLower       keyLower
     * @param valueUrlencode valueUrlencode
     * @return string
     */
    public static String mapJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            if (map.get(key) != null && !"".equals(map.get(key))) {
                try {
                    String temp = (key.endsWith("_") && key.length() > 1) ? key.substring(0, key.length() - 1) : key;
                    stringBuilder.append(keyLower ? temp.toLowerCase() : temp)
                            .append("=")
                            .append(valueUrlencode ? URLEncoder.encode(String.valueOf(map.get(key)), "utf-8").replace("+", "%20") : String.valueOf(map.get(key)))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据key值过滤map
     *
     * @param filterMap 待过滤map
     * @param keyList   key列表
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> filterKeys(Map<String, T> filterMap, List<String> keyList) {
        if (Objects.isNull(keyList) || keyList.isEmpty()) {
            return filterMap;
        }
        return Maps.filterKeys(filterMap, name -> keyList.contains(name));
    }

    public static <T> List<Map<String, T>> filterKeys(List<Map<String, T>> filterMapList,
                                                      List<String> keyList) {
        List<Map<String, T>> mapList = Lists.newArrayList();
        for (Map<String, T> map : filterMapList) {
            mapList.add(filterKeys(map, keyList));
        }
        return mapList;
    }

    public static boolean checkNullOrEmpty(Map map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * map中的key用驼峰结构转换
     *
     * @param map
     * @return
     */
    public static Map<String, Object> underlineToCamel(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();
            while (var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) var2.next();
                re_map.put(lineToHump(entry.getKey().toLowerCase()), map.get(entry.getKey()));
            }
            map.clear();
        }
        return re_map;
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String capitalFirst(String name) {
        return StringUtils.isNotEmpty(name) ? name.substring(0, 1).toUpperCase() + name.substring(1) : "";
    }
}
