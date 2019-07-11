package com.lixuan.smart_lock.service;


import com.lixuan.smart_lock.domain.TbApply;
import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.pojo.UserInfo;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Validated
public interface UserInfoService {

    String login(String userName, String password);

    Integer userRegistry(UserInfo userInfo, String identity);

    Integer deleteUser(int id);

    Integer updateUser(String userName, String password, String tel, String name, String idNumber, int id);

    List<TbUser> getUsers();

    List<TbApply> getApplies(Integer userId);

    List<TbHouse> getHouseList(Integer userId);

    List<TbApply> getSubletApplies(Integer userId);

    TbUser getUserInfo(Integer userId);

    String getTenantInfo(Integer userId);

}
