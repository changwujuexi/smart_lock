package com.lixuan.smart_lock.service.serviceImpl;

import com.lixuan.smart_lock.domain.TbApply;
import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.domain.TbRent;
import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.repository.TbApplyRepository;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.service.RentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentServiceImpl implements RentService {

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
    public static final int NO_PERMISSION = 401;

    @Override
    public Integer rentHouse(Integer houseId, Integer userId, String power) {
        TbRent tbRent = new TbRent();
        TbHouse tbHouse = tbHouseRepository.findById(houseId).get();
        TbRent tbRent1 = tbRentRepository.findByHouseIdAndStatus(houseId, "1");

        TbUser tbUser = tbUserRepository.findById(userId).get();

        List<TbApply> tbApplies = tbApplyRepository.findByHouseIdAndStatus(houseId, "0");

        try {
            if(tbRent1.getId() != null){
                throw new Exception();
            }
            tbRent.setPower(power);
            tbRent.setSublet("0");
            tbRent.setTenantId(userId);
            tbRent.setStatus("1");
            tbRent.setOwnerId(tbHouseRepository.findById(houseId).get().getOwnerId());
            tbRent.setHouseId(houseId);
            tbRent.setLastId(-1);
            tbRentRepository.save(tbRent);
            tbHouse.setRent("1");
            tbHouse.setUserId(userId);
            tbHouseRepository.save(tbHouse);

            for (TbApply tbApply:tbApplies) {
                tbApply.setStatus("1");
                tbApplyRepository.save(tbApply);
            }

            if(tbUser.getHouseId().equals("")){
                tbUser.setHouseId(houseId+"");
            }else {
                tbUser.setHouseId(tbUser.getHouseId()+","+houseId);
            }
            tbUserRepository.save(tbUser);

            return SUCCESS;
        }catch (Exception e){
            //已经出租了，返回401
            return NO_PERMISSION;
        }

    }

    @Override
    public Integer subletHouse(Integer houseId, Integer userId) {
        TbRent tbRent = new TbRent();
        TbRent tbRentBefore = tbRentRepository.findByHouseIdAndStatus(houseId, "1");
        TbHouse tbHouse = tbHouseRepository.findById(houseId).get();

        TbUser tbUser = tbUserRepository.findById(userId).get();
        TbUser tbUserBefore = tbUserRepository.findById(tbRentBefore.getTenantId()).get();

        List<TbApply> tbApplies = tbApplyRepository.findByHouseIdAndStatus(houseId, "0");

        String keys = tbHouse.getKeyNumber();
        boolean lable0 = keys.contains("0");
        boolean lable1 = keys.contains("1");

        String power = tbRentBefore.getPower();
        if(power.equals("0")){
            return NO_PERMISSION;
        }if(power.equals("1")){
            if(!lable0){
                return NO_PERMISSION;
            }
            tbRent.setPower("0");
        }if(power.equals("2")){
            if(!lable0&!lable1){
                return NO_PERMISSION;
            }
            tbRent.setPower("1");
        }

        if(tbRentBefore.getLastId() == -1){
            tbRent.setSublet("1");
        }else if(tbRentRepository.findById(tbRentBefore.getLastId()).get().getLastId() == -1){
            tbRent.setSublet("2");
        }else{
            return ERROR;
        }

        tbRent.setHouseId(houseId);
        tbRent.setStatus("1");
        tbRent.setTenantId(userId);
        tbRent.setOwnerId(tbHouse.getOwnerId());
        tbRent.setLastId(tbRentBefore.getId());

        tbRentBefore.setStatus("0");

        tbHouse.setUserId(userId);

        tbRentRepository.save(tbRentBefore);
        tbRentRepository.save(tbRent);
        tbHouseRepository.save(tbHouse);

        for (TbApply tbApply:tbApplies) {
            tbApply.setStatus("1");
            tbApplyRepository.save(tbApply);
        }

//        for (TbApply tbApply:tbApplies) {
//            tbApply.setStatus("1");
//            tbApplyRepository.save(tbApply);
//        }

        if(tbUser.getHouseId().equals("")){
            tbUser.setHouseId(houseId+"");
        }else {
            tbUser.setHouseId(tbUser.getHouseId()+","+houseId);
        }

        String houseIdBefore = tbUserBefore.getHouseId();
        houseIdBefore = houseIdBefore.replace(houseId+"","");
        houseIdBefore = houseIdBefore.replace(",,",",");
        if(houseIdBefore.startsWith(",")){
            houseIdBefore = houseIdBefore.substring(1,houseIdBefore.length());
        }if(houseIdBefore.endsWith(",")){
            houseIdBefore = houseIdBefore.substring(0,houseIdBefore.length()-1);
        }

        tbUserBefore.setHouseId(houseIdBefore);

        tbUserRepository.save(tbUser);
        tbUserRepository.save(tbUserBefore);

        return SUCCESS;
    }

    @Override
    public Integer endRent(Integer id) {
        try {
            TbRent tbRent = tbRentRepository.findById(id).get();
            TbHouse tbHouse = tbHouseRepository.findById(tbRent.getHouseId()).get();
            if(tbRent.getLastId() == -1){
                tbRent.setStatus("0");
                tbHouse.setUserId(-1);
            }else {
                TbRent tbRentBefore = tbRentRepository.findById(tbRent.getLastId()).get();
                tbRent.setStatus("0");
                tbRentBefore.setStatus("1");
                tbRentRepository.save(tbRentBefore);
                tbHouse.setUserId(tbRentBefore.getTenantId());
            }
            tbRentRepository.save(tbRent);
            tbHouseRepository.save(tbHouse);

            return SUCCESS;

        }catch (Exception e){
            return ERROR;
        }
    }

    @Override
    public Integer applyHouse(Integer userId, Integer houseId) {

        TbApply tbApply = new TbApply();
        tbApply.setHouseId(houseId);
        tbApply.setUserId(userId);
        tbApply.setStatus("0");
        tbApply.setAddr(tbHouseRepository.findById(houseId).get().getAddr());
        tbApply.setName(tbUserRepository.findById(userId).get().getName());
        tbApply.setTel(tbUserRepository.findById(userId).get().getTel());
        tbApplyRepository.save(tbApply);
        return SUCCESS;
    }

    @Override
    public String getHousePower(Integer userId) {

        String[] houseIds = tbUserRepository.findById(userId).get().getHouseId().split(",");

        Map<String, String> housePowers = new HashMap<>();
        for (String houseId: houseIds) {
            housePowers.put(houseId,tbRentRepository.findByHouseIdAndStatus(Integer.parseInt(houseId),"1").getPower());
        }

        String housePowerKeyValues = JSONObject.toJSONString(housePowers);
        return housePowerKeyValues;
    }
}
