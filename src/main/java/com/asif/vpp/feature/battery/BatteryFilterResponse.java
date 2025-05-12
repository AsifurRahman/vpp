package com.asif.vpp.feature.battery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryFilterResponse {
    private List<String> names;
    private double totalWattCapacity;
    private double averageWattCapacity;
}
