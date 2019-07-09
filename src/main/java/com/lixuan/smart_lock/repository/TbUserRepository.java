package com.lixuan.smart_lock.repository;

import com.lixuan.smart_lock.domain.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbUserRepository extends JpaRepository<TbUser, Integer> {
    TbUser findByUserName(String userName);
}
