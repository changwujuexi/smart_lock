package com.lixuan.smart_lock.service;

import com.lixuan.smart_lock.domain.UserEntity;
import com.lixuan.smart_lock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<ArrayList<String>> userLog(UserEntity userEntity) {

        List<ArrayList<String>> userList = new ArrayList<>();

        String username = userEntity.getUsername();
        String password = userEntity.getPassword();


        UserEntity userInSql = userRepository.findByUsername(username);
        if (!password.equals(userInSql.getPassword())) {
            ArrayList<String> message = new ArrayList<>();
            message.add("用户名与密码不匹配");
            userList.add(message);

            return userList;
        }

        List<UserEntity> userEntityList = userRepository.findAll();
        if (userInSql.getIdentity().equals("tenant")) {
            ArrayList<String> message = new ArrayList<>();
            message.add("租客登陆成功");
            userList.add(message);
        }

        else if (userInSql.getIdentity().equals("owner")) {

            ArrayList<String> applyProxyList = new ArrayList<>();
            ArrayList<String> proxyList = new ArrayList<>();
            ArrayList<String> applyRentList = new ArrayList<>();
            ArrayList<String> rentList = new ArrayList<>();
            for (UserEntity user : userEntityList) {

                if (user.isApplyProxy()) {
                    applyProxyList.add(user.getUsername());

                }
                if (user.isProxy()) {
                   proxyList.add(user.getUsername());
                }

                if (user.isApplyRent()) {
                    applyRentList.add(user.getUsername());
                }
                if (user.isRent()) {
                    rentList.add(user.getUsername());
                }

            }
            ArrayList<String> message = new ArrayList<>();
            message.add("所有者登陆成功");
            userList.add(message);
            userList.add(applyProxyList);
            userList.add(proxyList);
            userList.add(applyRentList);
            userList.add(rentList);
        }

        else {
            ArrayList<String> applyRentList = new ArrayList<>();
            ArrayList<String> rentList = new ArrayList<>();
            for (UserEntity user : userEntityList) {
                if (user.isApplyRent() && !user.isRent()) {
                    applyRentList.add(user.getUsername());
                }
                if (user.isRent()) {
                    rentList.add(user.getUsername());
                }
            }
            ArrayList<String> message = new ArrayList<>();
            message.add("代理者登陆成功");
            userList.add(message);
            userList.add(applyRentList);
            userList.add(rentList);

        }
        return userList;
    }
}
