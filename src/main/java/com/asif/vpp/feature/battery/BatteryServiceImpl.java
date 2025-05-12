package com.asif.vpp.feature.battery;

import com.asif.vpp.common.constant.ErrorId;
import com.asif.vpp.common.exception.RmsServerException;
import com.asif.vpp.config.io.BatteryWebSocketHandler;
import com.asif.vpp.feature.batterysave.BSStatus;
import com.asif.vpp.feature.batterysave.BatterySaveService;
import com.asif.vpp.feature.batterysave.BatterySaveStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BatteryServiceImpl implements BatteryService {
    private final BatteryRepository batteryRepository;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    protected static final Logger LOGGER = LoggerFactory.getLogger(BatteryServiceImpl.class);

    private final BatteryWebSocketHandler wsHandler;

    private final BatterySaveService batterySaveService;

    public BatteryServiceImpl(BatteryRepository batteryRepository, BatteryWebSocketHandler webSocketHandler,
                              BatteryWebSocketHandler wsHandler, BatterySaveService batterySaveService) {
        this.batteryRepository = batteryRepository;
        this.wsHandler = wsHandler;
        this.batterySaveService = batterySaveService;
    }


    @Transactional
    @Override
    public void bulkCreate(List<BatteryRequestDto> bulkBatteryRequestDto, String requestId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        BatterySaveStatus batterySaveStatus = batterySaveService.saveBatteryStatus(BSStatus.STARTED, userId, requestId);
        wsHandler.sendStatusToSession(batterySaveStatus);
        executor.submit(() -> {
            try {
                saveAllBatteriesTransactional(bulkBatteryRequestDto);
                LOGGER.info("Batteries Saved Successfully");
                wsHandler.sendStatusToSession(batterySaveService.updateBatteryStatus( BSStatus.SUCCESS, batterySaveStatus));
            } catch (Exception e) {
                LOGGER.error("Batteries Could Not be Saved", e);
                wsHandler.sendStatusToSession(batterySaveService.updateBatteryStatus( BSStatus.FAILED, batterySaveStatus));
            }

        });
    }


    public void saveAllBatteriesTransactional(List<BatteryRequestDto> requests) {
        List<Battery> batteries = requests.stream()
                .map(this::mapToEntity)
                .toList();
        batteryRepository.saveAll(batteries);
    }

    private Battery mapToEntity(BatteryRequestDto req) {
        Battery battery =  new Battery();
        battery.setName(req.getName());
        battery.setPostcode(req.getPostcode());
        battery.setWattCapacity(req.getWattCapacity());
        return battery;
    }

    @Override
    public BatteryFilterResponse filterBatteriesByRangeAndCapacity(int from, int to, Double min, Double max) {

        List<Battery> batteryList = batteryRepository.findByPostcodeBetween(from, to);

        List<Battery> filteredBatteries = batteryList.stream().filter(
                b -> b.getWattCapacity() >= min && b.getWattCapacity() <= max).toList();


        List<String> names = filteredBatteries.stream()
                .map(Battery::getName)
                .sorted()
                .toList();

        double totalCapacity = filteredBatteries.stream().mapToDouble(Battery::getWattCapacity).sum();
        double avgCapacity = filteredBatteries.isEmpty() ? 0 : totalCapacity / filteredBatteries.size();

        if (filteredBatteries.isEmpty()) {
            LOGGER.error("No batteryList found in the specified range and capacity.");
            throw RmsServerException.notFound(ErrorId.NOT_FOUND);
        }

        return BatteryFilterResponse.builder()
                .names(names)
                .totalWattCapacity(totalCapacity)
                .averageWattCapacity(avgCapacity)
                .build();
    }

    private BatteryResponseDto convertToResponseDto(Battery battery) {
        BatteryResponseDto batteryResponseDto = new BatteryResponseDto();
        batteryResponseDto.setId(battery.getId());
        batteryResponseDto.setName(battery.getName());
        batteryResponseDto.setPostcode(battery.getPostcode());
        return batteryResponseDto;
    }



}
