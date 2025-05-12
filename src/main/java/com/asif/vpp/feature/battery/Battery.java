package com.asif.vpp.feature.battery;

import com.asif.vpp.generic.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "battery")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Battery extends BaseEntity {

    private String name;
    private int postcode;
    private double wattCapacity;

}
