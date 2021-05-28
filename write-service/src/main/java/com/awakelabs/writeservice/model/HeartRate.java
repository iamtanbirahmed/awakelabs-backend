package com.awakelabs.writeservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "heart_rate")
public class HeartRate {
    /**
     * The DOM for accessing the table heart_rate
     */

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "time")
    private Long time;
    @Column(name = "motion")
    private Boolean motion;
    @Column(name = "patient_id")
    private Integer patientId;
    @Column(name = "phi_values")
    private String phiValues;
    @Column(name = "vital_sign_id")
    private UUID vitalSign;

    public HeartRate(Long time, Boolean motion, Integer patientId, String phiValues, UUID vitalSign) {
        this.time = time;
        this.motion = motion;
        this.patientId = patientId;
        this.phiValues = phiValues;
        this.vitalSign = vitalSign;
    }
    @Override
    public String toString() {
        return "HeartRate{" +
                "id=" + id +
                ", time=" + time +
                ", motion=" + motion +
                ", patientId=" + patientId +
                ", phiValues='" + phiValues + '\'' +
                ", vitalSign=" + vitalSign +
                '}';
    }
}
