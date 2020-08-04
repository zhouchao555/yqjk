package com.yhsl.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhsl.common.kit.BeanMapKit;
import com.yhsl.common.mapper.CommonMapper;
import com.yhsl.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<Map<String, Object>> query(String sql, TreeMap<String, Object> map) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        return commonMapper.query(map);

    }

    public Map<String, Object> queryPage(String sql, TreeMap<String, Object> map, int pageNum, int pageSize) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        int rowCount = commonMapper.count(map);
        map.put("sql", sql + " limit " + pageSize + " offset " + (pageNum - 1) * pageSize);
        List<Map<String, Object>> rowList = commonMapper.query(map);
        Map<String, Object> page = new HashMap<String, Object>();
        page.put("rowCount", rowCount);
        page.put("rowList", rowList);
        return page;

    }

    @Override
    @Transactional
    public Integer count(String sql, TreeMap<String, Object> map) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        return commonMapper.count(map);

    }

    @Override
    public Map<String, Object> queryOne(String sql, TreeMap<String, Object> map) {

        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql + " limit 1 offset 0");
        return commonMapper.queryOne(map);
    }

    @Override
    @Transactional
    public Integer insert(String sql, TreeMap<String, Object> map) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        return commonMapper.insert(map);

    }

    @Override
    @Transactional
    public Integer insert(String sql, Object obj) {

        TreeMap<String, Object> map = new TreeMap<>(BeanMapKit.Bean2Map(obj, null));
        map.put("sql", sql);
        return commonMapper.insert(map);

    }


    @Override
    @Transactional
    public Integer update(String sql, TreeMap<String, Object> map) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        return commonMapper.update(map);
    }

    @Override
    @Transactional
    public Integer update(String sql, Object obj) {

        TreeMap<String, Object> map = new TreeMap<>(BeanMapKit.Bean2Map(obj, null));
        map.put("sql", sql);
        return commonMapper.update(map);

    }

    @Override
    @Transactional
    public Integer delete(String sql, TreeMap<String, Object> map) {
        if (Objects.isNull(map)) {
            map = new TreeMap<String, Object>();
        }
        map.put("sql", sql);
        return commonMapper.delete(map);

    }


}
