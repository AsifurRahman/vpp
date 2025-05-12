package com.asif.vpp.feature.battery;

import com.asif.vpp.generic.payload.request.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatteryRequestDto implements IDto {
    private String name;
    private int postcode;
    private double wattCapacity;

}
