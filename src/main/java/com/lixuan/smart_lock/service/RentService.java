package com.lixuan.smart_lock.service;

import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
public interface RentService {

    Integer rentHouse(Integer houseId, Integer userId, String power);

    Integer subletHouse(Integer houseId, Integer userId);

    Integer endRent(Integer id);

    Integer applyHouse(Integer userId, Integer houseId);

    String[][] getHousePower(Integer userId);

}
