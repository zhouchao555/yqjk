package com.yhsl.business.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Data
@TableName("orderinfo")//不添加tableName，默认映射类名
public class Order implements Serializable {
    private String name;
    private int money;


}
