package com.asif.vpp.feature.battery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetween(int start, int end);
}
