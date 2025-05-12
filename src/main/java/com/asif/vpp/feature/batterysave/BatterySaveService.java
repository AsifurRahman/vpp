package com.asif.vpp.feature.batterysave;

import com.asif.vpp.common.constant.ErrorId;
import com.asif.vpp.common.exception.RmsServerException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BatterySaveService {


    private final BatterySaveStateRepository batterySaveStateRepository;

    public BatterySaveService(BatterySaveStateRepository batterySaveStateRepository) {
        this.batterySaveStateRepository = batterySaveStateRepository;
    }


    public BatterySaveStatus saveBatteryStatus(BSStatus status, String userId, String requestId) {
        BatterySaveStatus batterySaveStatus = new BatterySaveStatus();
        batterySaveStatus.setStatus(status.name());
        batterySaveStatus.setRequestId(requestId);
        batterySaveStatus.setMessage(status.getMessage());
        return batterySaveStateRepository.save(batterySaveStatus);
    }

    public BatterySaveStatus updateBatteryStatus(BSStatus status, BatterySaveStatus batterySaveStatus) {
        batterySaveStatus.setStatus(status.name());
        batterySaveStatus.setMessage(status.getMessage());
        return batterySaveStateRepository.save(batterySaveStatus);
    }
    public BatterySaveStatus getBatterySaveStatus(Long id) {
        return batterySaveStateRepository.findById(id)
                .orElseThrow(() ->  RmsServerException.notFound(ErrorId.NOT_FOUND));
    }




}
