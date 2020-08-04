package com.yhsl.business.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhsl.business.demo.dto.UserInfo;
import com.yhsl.business.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@Repository
public interface UserMapper extends BaseMapper<User>{

    User findUser(@Param("name") String name, @Param("phone") String phone);
    List<User> findUsers();
    List<User> findAll();
    @Select("SELECT * FROM user WHERE name like #{m.name} ORDER BY `id` DESC")
    List<User> selectUserPage(@Param("m") Map<String,Object> m, Page<User> page);
    @Select("SELECT * FROM user u inner join orderinfo o WHERE u.name like #{m.name} ORDER BY u.id DESC")
    List<UserInfo> selectUserInfoPage(@Param("m") Map<String,Object> m, Page<User> page);


}
