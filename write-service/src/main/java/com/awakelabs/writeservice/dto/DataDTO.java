package com.awakelabs.writeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {

    private Integer currentBpm;
    private Integer anxietyLevel;
    private Integer baselineProgress;
    private Set<HeartRateDTO> heartRates;
    private Long time;
    private StateDTO state;
}
