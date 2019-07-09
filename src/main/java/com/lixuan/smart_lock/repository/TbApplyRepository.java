package com.lixuan.smart_lock.repository;

import com.lixuan.smart_lock.domain.TbApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbApplyRepository extends JpaRepository<TbApply, Integer> {
    List<TbApply> findByHouseIdAndStatus(Integer houseId, String status);
    List<TbApply> findByUserIdAndStatus(Integer userId, String status);
}
