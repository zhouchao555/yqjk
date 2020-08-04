package com.yhsl.business.demo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)//全参构造器
@NoArgsConstructor(access = AccessLevel.PUBLIC)//无参构造器
public class User implements Serializable {


    private String id;
    private String name;
    private String phone;
    private int status;


}
