package com.lixuan.smart_lock.service;


import com.lixuan.smart_lock.domain.TbHouse;
import com.lixuan.smart_lock.pojo.HouseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface HouseService {

    Integer addHouse(HouseInfo houseInfo, Integer ownerId);

    Integer addKey(Integer id, String key, String keyName);

    Integer deleteHouse(Integer id);

    Integer deleteKey(Integer id, Integer number);

    Integer forbidRent(Integer id);

    Integer allowRent(Integer id);

    List<TbHouse> getHouses();

    List<TbHouse> getAvailableHouses();

    List<String> getKeys(Integer id);

    List<String> getKeyNames(Integer id);

}
