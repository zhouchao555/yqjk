package com.yhsl.business.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhsl.business.demo.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Repository
public interface OrderMapper extends BaseMapper<Order>{

    @Select("select * from Orderinfo where id =#{id} ")
    List<Order> findBySql(@Param("id")String id);

}
