package com.awakelabs.writeservice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TimeSeriesValueDTO {

    /**
     * DTO used to read-write hear-rate information in the redis time-series cache
     */

    private Long time;
    private Long value;

}
