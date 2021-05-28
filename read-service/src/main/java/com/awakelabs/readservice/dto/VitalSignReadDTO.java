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
public class VitalSignReadDTO {
    private UUID id;
    private Integer patientId;
    private Double currentBpm;
    private Double anxietyLevel;
    private Double baselineProgress;
    private Long time;
    private Double batteryLevel;


}
