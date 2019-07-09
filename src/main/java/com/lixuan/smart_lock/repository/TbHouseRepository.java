package com.lixuan.smart_lock.repository;

import com.lixuan.smart_lock.domain.TbHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbHouseRepository extends JpaRepository<TbHouse, Integer> {
    List<TbHouse> findByRent(String rent);
}
