package com.asif.vpp.feature.batterysave;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatterySaveStateRepository extends JpaRepository<BatterySaveStatus, Long> {
    Optional<BatterySaveStatus> findByRequestId(String requestId);
}
