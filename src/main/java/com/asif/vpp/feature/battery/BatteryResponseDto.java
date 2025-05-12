package com.asif.vpp.feature.battery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryResponseDto {
    private Long id;
    private String name;
    private int postcode;
    private double wattCapacity;
}
