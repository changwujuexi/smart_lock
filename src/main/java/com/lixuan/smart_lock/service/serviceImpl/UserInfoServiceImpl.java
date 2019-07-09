package com.lixuan.smart_lock.service.serviceImpl;

import com.lixuan.smart_lock.domain.TbApply;
import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.pojo.UserInfo;
import com.lixuan.smart_lock.repository.TbApplyRepository;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    TbUserRepository tbUserRepository;

    @Autowired
    TbHouseRepository tbHouseRepository;

    @Autowired
    TbRentRepository tbRentRepository;

    @Autowired
    TbApplyRepository tbApplyRepository;

    public static final int SUCCESS = 200;
    public static final int ERROR = 400;
    public static final int NOTEXIST = 401;


    @Override
    public String login(String userName, String password) {

        TbUser tbUser = tbUserRepository.findByUserName(userName);
        if(tbUser == null){
            return null;
        }
        String password_ind = tbUser.getPassword();
        if(password.equals(password_ind)) {
            return tbUser.getId()+","+tbUser.getIdentity();

        }
        return null;
    }

    @Override
    public Integer userRegistry(UserInfo userInfo, String identity) {

        TbUser tbUser = new TbUser();
        if(tbUserRepository.findByUserName(userInfo.getUserName()) != null){
            return ERROR;
        }
        tbUser.setUserName(userInfo.getUserName());
        tbUser.setIdNumber(userInfo.getIdNumber());
        tbUser.setName(userInfo.getName());
        tbUser.setPassword(userInfo.getPassword());
        tbUser.setTel(userInfo.getTel());
        tbUser.setHouseId("");
        tbUser.setIdentity(identity);
        tbUserRepository.save(tbUser);
        return SUCCESS;
    }

    @Override
    public Integer deleteUser(int id) {
        try {
            TbUser tbUser = tbUserRepository.findById(id).get();
            tbUserRepository.delete(tbUser);
            return SUCCESS;
        }catch (Exception e){
            return ERROR;
        }
    }

    @Override
    public Integer updateUser(String userName, String password, String tel, String name, String idNumber, int id) {
        if(tbUserRepository.findById(id).isPresent()){
            TbUser tbUser = tbUserRepository.findById(id).get();
            if(userName!=null){
                tbUser.setUserName(userName);
            }
            if(password!=null){
                tbUser.setPassword(password);
            }
            if(tel!=null){
                tbUser.setTel(tel);
            }
            if(name!=null){
                tbUser.setName(name);
            }
            if(idNumber!=null){
                tbUser.setIdNumber(idNumber);
            }
            tbUserRepository.save(tbUser);
            return SUCCESS;
        }
        return ERROR;
    }

    @Override
    public List<TbUser> getUsers() {
        return tbUserRepository.findAll();
    }


    //获得所有名下房产（房东为本来所有的房子，房客为转租中的房子）的申请信息
    @Override
    public List<TbApply> getApplies(Integer userId) {
//        if (tbUserRepository.findById(userId).get().getIdentity().equals("1")){

        if(tbUserRepository.findById(userId).get().getHouseId().equals("")){
            return null;
        }else {
            String[] houses = tbUserRepository.findById(userId).get().getHouseId().split(",");
            List<TbApply> tbApplies = new ArrayList<>();
            for(int i = 0;i<houses.length;i++){
                tbApplies.addAll(tbApplyRepository.findByHouseIdAndStatus(Integer.parseInt(houses[i]), "0"));
            }
            return tbApplies;
        }
//         }
//
//        if(tbUserRepository.findById(userId).get().getIdentity().equals("0")){
//            return tbApplyRepository.findByUserIdAndStatus(userId, "0");
//        }
//        return null;
    }

    //获得房客的房屋申请信息
    @Override
    public List<TbApply> getSubletApplies(Integer userId) {
        return tbApplyRepository.findByUserIdAndStatus(userId,"0");
    }

    @Override
    public List<TbHouse> getHouseList(Integer userId) {

        String[] houses = tbUserRepository.findById(userId).get().getHouseId().split(",");
        List<TbHouse> tbHouses = new ArrayList<>();
        for (String house:houses) {
            tbHouses.add(tbHouseRepository.findById(Integer.parseInt(house)).get());
        }
        return tbHouses;
    }
}
