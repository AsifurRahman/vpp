package com.asif.vpp.feature.battery;

import java.util.List;

public interface BatteryService {

    void bulkCreate(List<BatteryRequestDto> bulkBatteryRequestDto, String requestId);

    BatteryFilterResponse filterBatteriesByRangeAndCapacity(int from, int to, Double min, Double max);
}
