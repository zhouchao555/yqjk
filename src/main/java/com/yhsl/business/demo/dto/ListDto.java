package com.yhsl.business.demo.dto;

import com.yhsl.business.demo.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Data
public class ListDto implements Serializable {
    private String id;
    private String phone;
    private List<User> list;

}
