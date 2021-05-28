package com.awakelabs.writeservice.dto;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeartRateDTO {

    private Long time;
    private Double rrInterval;
    private Double heartRate;
    private Double anxietyLevel;
    private Boolean motion;


}
