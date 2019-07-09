package com.lixuan.smart_lock.repository;

import com.lixuan.smart_lock.domain.TbRent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbRentRepository extends JpaRepository<TbRent, Integer> {
    TbRent findByHouseId(Integer houseId);
    TbRent findByHouseIdAndStatus(Integer houseId, String status);
}
