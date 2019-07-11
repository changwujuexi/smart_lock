package com.lixuan.smart_lock.controller;

import com.lixuan.smart_lock.domain.TbUser;
import com.lixuan.smart_lock.pojo.UserInfo;
import com.lixuan.smart_lock.repository.TbHouseRepository;
import com.lixuan.smart_lock.repository.TbRentRepository;
import com.lixuan.smart_lock.repository.TbUserRepository;
import com.lixuan.smart_lock.service.HouseService;
import com.lixuan.smart_lock.service.RentService;
import com.lixuan.smart_lock.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RentController {

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

    @PostMapping(value = "/rent")
    public Integer houseRent(@RequestParam("houseid") Integer houseId,
                               @RequestParam("userid") Integer userId,
                               @RequestParam("power") String power) {

        int code = rentService.rentHouse(houseId,userId,power);
        return code;
    }

    @PostMapping(value = "/sublet")
    public Integer houseSublet(@RequestParam("houseid") Integer houseId,
                                @RequestParam("userid") Integer userId) {

        int code = rentService.subletHouse(houseId,userId);
        return code;
    }

    @PostMapping(value = "/endrent")
    public Integer houseEndRent(@RequestParam("houseid") Integer houseId) {

        int code = rentService.endRent(houseId);
        return code;
    }

    @PostMapping(value = "/apply")
    public Integer houseApply(@RequestParam("houseid") Integer hosueId,
                              @RequestParam("userid") Integer userId){
        int code = rentService.applyHouse(userId, hosueId);
        return code;
    }

    @GetMapping(value = "/power")
    public String[][] housePower(@RequestParam("userid") Integer userId){
        return rentService.getHousePower(userId);
    }



}
