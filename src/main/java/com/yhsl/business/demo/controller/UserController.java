package com.yhsl.business.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhsl.business.demo.dto.ListDto;
import com.yhsl.business.demo.entity.Order;
import com.yhsl.business.demo.entity.User;
import com.yhsl.business.demo.mapper.OrderMapper;
import com.yhsl.business.demo.mapper.UserMapper;
import com.yhsl.business.demo.service.UserService;
import com.yhsl.common.kit.UuidKit;
import com.yhsl.common.mapper.CommonMapper;
import com.yhsl.common.service.CommonService;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ Author     ：zhouchao.
 * @ Date       ：Created in 2019-09-04
 * @ Description：
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonMapper commonMapper;



    @PostMapping("/saveUser")
    public String saveUser(String name, String phone){
        User user = new User(UuidKit.getID16(),name, phone,0);
        userService.saveUser(user);
        return "save sucess";
    }

    @PostMapping("/deleteUser")//物理删除
    public String deleteUser(String id){
        userService.deleteUser(id);
        return "delete sucess";
    }

    @PostMapping("/updateUser")
    public String updateUser(String id, String name, String phone){
        User user = new User(id, name, phone, 0);
//        userService.updateUser(user);
        userMapper.updateById(user);
        return "update sucess";
    }

    @GetMapping("/getUserById")
    public User getUserById(String id){
        User temp = userService.getUserById(id);
        return temp;
    }


    @GetMapping("/getUser")
    public User getUser(String name, String phone){
        User temp = userService.getUser(name, phone);
        return temp;
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        List<User> list = userService.getUsers();
        return list;
    }

    @GetMapping("/getUserInfo")
    public List<User> getUserInfo(){
        List<User> list = userService.getUserInfo();
        return list;
    }

    @GetMapping("/getList")
    public List<User> getList(){
        List<User> list = userMapper.selectList(null);
        return list;
    }

    @GetMapping("/getOrderList")
    public List<Order> getOrderList(){
        List<Order> list = orderMapper.selectList(new QueryWrapper<Order>().like("name","篮球"));
        return list;
    }

    @GetMapping("/getCommonList")
    public List<Map<String, Object>> getCommonList(HttpServletRequest request){
        String name = request.getParameter("name");

        StringBuilder sb = new StringBuilder("select * from user where status=0");
        TreeMap map = new TreeMap();
        if(!Strings.isNullOrEmpty(name)){
            sb.append(" and name like %#{name}%");
            map.put("name","%" + name + "%");
        }
        List<Map<String, Object>> list = commonService.query(sb.toString(), map);
        return list;
    }

    @GetMapping("/getCommonOne")
    public Map<String, Object> getCommonOne(HttpServletRequest request){
        String name = request.getParameter("name");

        StringBuilder sb = new StringBuilder("select * from user u left join orderinfo o on o.userId=u.id where u.status=0");
        TreeMap map = new TreeMap();
        if(!Strings.isNullOrEmpty(name)){
            sb.append(" and name like #{name}");
            map.put("name","%" + name + "%");
        }
        Map<String, Object> map1 = commonService.queryOne(sb.toString(), map);
        return map1;
    }

    @GetMapping("/getCommonPage")
    public Map<String, Object> getCommonPage(HttpServletRequest request){

        int pageNum = Integer.valueOf(request.getParameter("pageNum"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        String name = request.getParameter("name");

        StringBuffer sb = new StringBuffer("select u.id,u.name,o.money from user u left join orderinfo o on o.userId=u.id where u.status=0");
        TreeMap map = new TreeMap();
        if(!Strings.isNullOrEmpty(name)){
            sb.append(" and name like #{name}");
            map.put("name","%" + name + "%");
        }
        Map<String, Object> map1 = commonService.queryPage(sb.toString(), map, pageNum, pageSize );
        return map1;
    }

    @GetMapping("/queryWrapperPage")
    public IPage<Order> queryWrapperPage(int pageNum, int pageSize){

        QueryWrapper<Order> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("status",0);
        queryWrapper.orderByDesc("name");

        Page<Order> page = new Page<>(pageNum, pageSize);
        IPage<Order> iPage = orderMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @GetMapping("/queryUserPage")
    public Map<String, Object> queryUserPage(int pageNum, int pageSize){

        Map<String,Object> m = new HashMap<>();
        m.put("name", "%k%");
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectUserPage(m, null).size();

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rowList", userMapper.selectUserPage(m, page));
        result.put("rowCount", userMapper.selectUserPage(m, null).size());

        return result;
    }

    @GetMapping("/queryUserInfoPage")
    public Map<String, Object> queryUserInfoPage(int pageNum, int pageSize){

        Map<String,Object> m = new HashMap<>();
        m.put("name", "%k%");
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectUserPage(m, null).size();

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rowList", userMapper.selectUserInfoPage(m, page));
        result.put("rowCount", userMapper.selectUserInfoPage(m, null).size());

        return result;
    }

    @PostMapping("/commonInsert")
    public void commonInsert(HttpServletRequest request){

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        User user = new User(UuidKit.getID16(),name, phone,0);

//        userMapper.insert(user);
        commonService.insert("insert into user values(#{id},#{name},#{phone},#{status})", user);
    }

    @PostMapping("/modelInsert")
    public void modelInsert(User user){

        user.setId(UuidKit.getID16());
        user.setStatus(0);
        
//        userMapper.insert(user);
        commonService.insert("insert into user values(#{id},#{name},#{phone},#{status})", user);
    }

    @PostMapping("/modelsInsert")
    public void modelsInsert(User user,Order order){

        user.setId(UuidKit.getID16());
        user.setStatus(0);

//        userMapper.insert(user);
        commonService.insert("insert into user values(#{id},#{name},#{phone},#{status})", user);
    }


    @PostMapping("/commonUpdate")
    public void commonUpdate(HttpServletRequest request){

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        User user = new User(id,name, phone,0);

//        userMapper.insert(user);
        commonService.update("update user set name=#{name},phone=#{phone} where id=#{id}", null);
    }

    @PostMapping("/batchUpdate")
    public void batchUpdate(HttpServletRequest request){

        User user = new User("111","kk", "1111111",0);
        User user2 = new User("222","pp", "222222",0);
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        userService.updateBatchById(list);
    }

    @PostMapping(value="/batchInsert")
    public void batchInsert(@RequestBody ListDto list){

//        User user = new User(UuidKit.getID16(),"lily", "1946736157",0);
//        User user2 = new User(UuidKit.getID16(),"lucy", "1694736134",0);
//        List<User> list = new ArrayList<User>();
//        list.add(user);
//        list.add(user2);

          List<User> userList = list.getList();
          System.out.println(list.toString());
          userService.saveBatch(userList);

    }


    @PostMapping(value="/batchInsert2")
    @Transactional
    public void batchInsert2(){

        User user = new User("111","lily", "1946736157",0);
        User user2 = new User("222","lucy", "1694736134",0);
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        userService.saveBatch(list);
        int i = 1/0;

    }

    @PostMapping(value="/selectBySql")
    public List<Order> selectBySql(HttpServletRequest request){
        String id = request.getParameter("id");
        return orderMapper.findBySql(id);
    }


    @PostMapping(value="/transaction")
    @Transactional
    public Integer transaction(HttpServletRequest request){

        return commonService.insert("insert into user value('8888','tom','6666',0),('9999','kill','5555',0)",null);
    }

    @PostMapping(value="/transaction2")
    @Transactional
    public Integer transaction2(HttpServletRequest request){

        for(int i = 0;  i < 10; i++){
            commonService.insert("insert into user value('"+i+"','tom','6666',0)",null);
        }
        int i = 1/0;
        return 1;
    }

    @PostMapping(value="/file")
    @Transactional
    public void file(HttpServletRequest request, MultipartFile[] file){

        System.out.println(file.length);
    }

}
