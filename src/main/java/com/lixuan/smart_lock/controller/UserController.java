package com.lixuan.smart_lock.controller;


import com.lixuan.smart_lock.domain.TbApply;
import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.pojo.UserInfo;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.repository.UserRepository;


import com.lixuan.smart_lock.service.HouseService;
import com.lixuan.smart_lock.service.RentService;
import com.lixuan.smart_lock.service.UserInfoService;
import com.lixuan.smart_lock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TbUserRepository tbUserRepository;

    @Autowired
    TbHouseRepository tbHouseRepository;

    @Autowired
    TbRentRepository tbRentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private RentService rentService;

    //用户注册
    @PostMapping(value = "/users/registration")
    public String userRegistry(@RequestBody UserInfo userInfo,
                               @RequestParam("identity") String identity) {

        int code = userInfoService.userRegistry(userInfo,identity);
        if (code == 200){
            return "用户注册成功";
        }if(code == 400){
            return "该用户名已被注册";
        }
        return "未知错误";
    }

    //用户登陆
    @PostMapping(value = "/users/log")
    public String log(@RequestParam("username") String userName,
                       @RequestParam("password") String password) {

        String code = userInfoService.login(userName, password);
        return code;

    }

    //删除用户
    @DeleteMapping(value = "/users/delete")
    public Integer deleteUsers(@RequestParam("id") Integer id){
        Integer code = userInfoService.deleteUser(id);
        return code;
    }

    //修改用户信息
    @PostMapping(value = "/users/update")
    public Integer updateUsers(@RequestParam("id") Integer id,
                               @RequestParam("username") String userName,
                               @RequestParam("password") String password,
                               @RequestParam("tel") String tel,
                               @RequestParam("name") String name,
                               @RequestParam("idnumber") String idNumber){
        Integer code = userInfoService.updateUser(userName,password,tel,name,idNumber,id);
        return code;
    }

    //获得所有用户信息
    @GetMapping(value = "/users/list")
    public List<TbUser> getUserList(){
        return userInfoService.getUsers();
    }

    //获得所有租房申请
    @GetMapping(value = "/users/apply")
    public List<TbApply> getApplyList(@RequestParam("id") Integer id){
        return userInfoService.getApplies(id);
    }

    @GetMapping(value = "/users/subletapply")
    public List<TbApply> getSubletApplyList(@RequestParam("id") Integer id){
        return userInfoService.getSubletApplies(id);
    }

    //获得所有拥有的房子信息
    @GetMapping(value = "/users/houses")
    public List<TbHouse> getHouseList(@RequestParam("id") Integer id){
        return userInfoService.getHouseList(id);
    }



}
