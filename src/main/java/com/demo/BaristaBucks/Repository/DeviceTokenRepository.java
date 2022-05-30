package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    Long deleteByDeviceToken(String deviceToken);
    List<DeviceToken> findByUserId(String userId);
    List<DeviceToken> findByUserIdIn(List<String> userId);

    DeviceToken findByDeviceToken(String deviceToken);
}