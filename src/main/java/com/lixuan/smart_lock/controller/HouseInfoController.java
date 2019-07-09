package com.lixuan.smart_lock.controller;


import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.pojo.HouseInfo;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.service.HouseService;
import com.lixuan.smart_lock.service.RentService;
import com.lixuan.smart_lock.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HouseInfoController {

    @Autowired
    TbUserRepository tbUserRepository;

    @Autowired
    TbHouseRepository tbHouseRepository;

    @Autowired
    TbRentRepository tbRentRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private RentService rentService;

    @PostMapping(value = "/addhouse")
    public Integer addHouse(HouseInfo houseInfo,
                            @RequestParam("ownerid") Integer ownerId) {
        int code = houseService.addHouse(houseInfo, ownerId);
        return code;
    }

    @PostMapping(value = "/addkey")
    public Integer addKey(@RequestParam("houseid") Integer houseId,
                          @RequestParam("key") String key,
                          @RequestParam("keyName") String keyname) {
        int code = houseService.addKey(houseId, key, keyname);
        return code;
    }

    @DeleteMapping(value = "/deletehouse")
    public Integer deleteHouse(@RequestParam("houseid") Integer houseId) {
        int code = houseService.deleteHouse(houseId);
        return code;
    }

    @DeleteMapping(value = "/deletekey")
    public Integer deleteKey(@RequestParam("houseid") Integer houseId,
                             @RequestParam("number") Integer number) {
        int code = houseService.deleteKey(houseId,number);
        return code;
    }

    @PostMapping(value = "/forbidrent")
    public Integer forbidRent(@RequestParam("houseid") Integer houseId) {
        int code = houseService.forbidRent(houseId);
        return code;
    }

    @PostMapping(value = "/allowrent")
    public Integer allowrent(@RequestParam("houseid") Integer houseId) {
        int code = houseService.allowRent(houseId);
        return code;
    }

    @GetMapping(value = "/houses")
    public List<TbHouse> getHouses() {
        return houseService.getHouses();
    }

    @GetMapping(value = "/avahouses")
    public List<TbHouse> getAvailableHouses() {

        return houseService.getAvailableHouses();
    }

    @GetMapping(value = "/keys")
    public List<String> getKeys(@RequestParam("houseid") Integer houseId) {

        return houseService.getKeys(houseId);
    }

    @GetMapping(value = "/keynames")
    public List<String> getKeyNames(@RequestParam("houseid") Integer houseId) {

        return houseService.getKeyNames(houseId);
    }


}
