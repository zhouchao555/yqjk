package com.yhsl.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface CommonService  {
    List<Map<String, Object>> query(String sql, TreeMap<String, Object> map);

    Map<String, Object> queryPage(String sql, TreeMap<String, Object> map, int pageNum, int pageSize);

    Integer count(String sql, TreeMap<String, Object> map);

    Map<String, Object> queryOne(String sql, TreeMap<String, Object> map);

    @Transactional
    Integer insert(String sql, TreeMap<String, Object> map);

    @Transactional
    Integer insert(String sql, Object obj);

    @Transactional
    Integer update(String sql, TreeMap<String, Object> map);

    @Transactional
    Integer update(String sql, Object obj);

    @Transactional
    Integer delete(String sql, TreeMap<String, Object> map);

}
