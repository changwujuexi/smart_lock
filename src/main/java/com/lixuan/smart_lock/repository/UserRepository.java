package com.lixuan.smart_lock.repository;

import com.lixuan.smart_lock.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username);
}
