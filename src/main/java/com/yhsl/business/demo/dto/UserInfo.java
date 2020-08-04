package com.yhsl.business.demo.dto;

import com.yhsl.business.demo.entity.Order;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Data
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String phone;
    private int money;
    private String status;
    private List<Order> orders;


}
