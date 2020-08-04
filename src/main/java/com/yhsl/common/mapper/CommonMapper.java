package com.yhsl.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface CommonMapper {

    List<Map<String, Object>> query(TreeMap<String, Object> map);
    Integer count(TreeMap<String, Object> map);
    Map<String, Object> queryOne(TreeMap<String, Object> map);
    Integer insert(TreeMap<String, Object> map);
    Integer update(TreeMap<String, Object> map);
    Integer delete(TreeMap<String, Object> map);
}
