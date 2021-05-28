package com.awakelabs.readservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeartRateReadDTO {
    private UUID id;
    private Long time;
    private Double rrInterval;
    private Double heartRate;
    private Double anxietyLevel;
    private Boolean motion;
    private Integer patientId;
    private UUID vitalSign;
}
