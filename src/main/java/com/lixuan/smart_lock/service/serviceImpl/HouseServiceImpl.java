package com.lixuan.smart_lock.service.serviceImpl;

import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.pojo.HouseInfo;
import com.lixuan.smart_lock.repository.TbApplyRepository;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

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

    @Override
    public Integer addHouse(HouseInfo houseInfo, Integer ownerId) {

        TbHouse tbHouse = new TbHouse();
        tbHouse.setAddr(houseInfo.getAddr());
        tbHouse.setKeyNumber(houseInfo.getKey());
        tbHouse.setKeyName(houseInfo.getKeyName());
        tbHouse.setOwnerId(ownerId);
        tbHouse.setUserId(-1);
        tbHouse.setRent("0");
        tbHouseRepository.save(tbHouse);
        TbUser tbUser = tbUserRepository.findById(ownerId).get();
        if(tbUser.getHouseId().equals("")||tbUser.getHouseId() == null){
            tbUser.setHouseId(""+tbHouse.getId());
            tbUserRepository.save(tbUser);
        }else {
            tbUser.setHouseId(tbUser.getHouseId()+","+tbHouse.getId());
            tbUserRepository.save(tbUser);
        }
        return SUCCESS;
    }

    @Override
    public Integer addKey(Integer id, String key, String keyName) {
        TbHouse tbHouse = tbHouseRepository.findById(id).get();
        if(tbHouse.getKeyNumber() != null){
            tbHouse.setKeyNumber(tbHouse.getKeyNumber()+","+key);
            tbHouse.setKeyName(tbHouse.getKeyName()+","+keyName);
            tbHouseRepository.save(tbHouse);
            return SUCCESS;
        }
        tbHouse.setKeyNumber(key);
        tbHouse.setKeyName(keyName);
        tbHouseRepository.save(tbHouse);
        return SUCCESS;
    }

    @Override
    public Integer deleteHouse(Integer id) {

        if(tbRentRepository.findByHouseIdAndStatus(id,"1")!=null){
            return 401;
        }

        try {

            TbUser tbUser = tbUserRepository.findById(tbHouseRepository.findById(id).get().getOwnerId()).get();
            String Houses = tbUser.getHouseId();

            StringBuffer newHouses = new StringBuffer();
            String[] HousesString = Houses.split(",");
            for (String house: HousesString) {
                if (house.equals(id+"")) {
                    continue;
                }
                newHouses.append(house);
                newHouses.append(",");
            }
            Houses = newHouses.substring(0);
            Houses = Houses.replace(",,",",");
            if (Houses.startsWith(",")){
                Houses = Houses.substring(1);
            }
            if (Houses.endsWith(",")){
                Houses = Houses.substring(0,Houses.length()-1);
            }
            tbUser.setHouseId(Houses);

            tbUserRepository.save(tbUser);



            tbHouseRepository.delete(tbHouseRepository.findById(id).get());
            return SUCCESS;
        }catch (Exception e){
            return ERROR;
        }
    }

    @Override
    public Integer deleteKey(Integer id, Integer number) {
        try {
            TbHouse tbHouse = tbHouseRepository.findById(id).get();
            String[] keys = tbHouse.getKeyNumber().split(",");
            String[] keyNames = tbHouse.getKeyName().split(",");
            String newkeys = "";
            String newkeysNames = "";
            for (int i = 0; i < keys.length; i++){
                if(i != number-1){
                    newkeys = newkeys+keys[i];
                    newkeysNames = newkeysNames+keyNames[i];
                }
            }
            if(newkeys.equals("")){
                tbHouse.setKeyName(null);
                tbHouse.setKeyNumber(null);
                tbHouseRepository.save(tbHouse);
            }
            tbHouse.setKeyNumber(newkeys);
            tbHouse.setKeyName(newkeysNames);
            tbHouseRepository.save(tbHouse);
            return SUCCESS;

        }catch (Exception e){
            return ERROR;
        }

    }

    @Override
    public Integer forbidRent(Integer id) {
        TbHouse tbHouse = tbHouseRepository.findById(id).get();
        tbHouse.setRent("1");
        tbHouseRepository.save(tbHouse);
        return SUCCESS;
    }

    @Override
    public Integer allowRent(Integer id) {
        if(tbRentRepository.findByHouseIdAndStatus(id,"1") != null){
            if (tbRentRepository.findByHouseIdAndStatus(id,"1").getPower().equals("2")){
                return 401;
            }
        }
        TbHouse tbHouse = tbHouseRepository.findById(id).get();
        tbHouse.setRent("0");
        tbHouseRepository.save(tbHouse);
        return SUCCESS;
    }

    @Override
    public List<TbHouse> getHouses() {
        return tbHouseRepository.findAll();
    }

    @Override
    public List<TbHouse> getAvailableHouses() {
        return tbHouseRepository.findByRent("0");
    }

    @Override
    public List<String> getKeys(Integer id) {
        try {
            TbHouse tbHouse = tbHouseRepository.findById(id).get();
            String[] keys = tbHouse.getKeyNumber().split(",");
            List<String> keylist = new ArrayList<>();
            for (int i = 0; i < keys.length; i++) {
                keylist.add(keys[i]);
            }
            return keylist;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<String> getKeyNames(Integer id) {
        try {
            TbHouse tbHouse = tbHouseRepository.findById(id).get();
            String[] keyNames = tbHouse.getKeyName().split(",");
            List<String> keyNamelist = new ArrayList<>();
            for (int i = 0; i < keyNames.length; i++) {
                keyNamelist.add(keyNames[i]);
            }
            return keyNamelist;
        }catch (Exception e){
            return null;
        }
    }
}
