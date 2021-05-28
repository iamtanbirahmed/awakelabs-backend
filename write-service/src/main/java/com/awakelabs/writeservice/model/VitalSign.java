package com.awakelabs.writeservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "vital_sign")
public class VitalSign {
    /**
     * DOM to access the table vital_sign
     */
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "patient_id")
    private Integer patientId;
    @Column(name = "time")
    private Long time;
    @Column(name = "battery_level")
    private Double batteryLevel;
    @Column(name = "phi_values")
    private String phiValues;


    public VitalSign(Integer patientId, Long time, Double batteryLevel, String phiValues) {
        this.patientId = patientId;
        this.time = time;
        this.batteryLevel = batteryLevel;
        this.phiValues = phiValues;
    }

}
