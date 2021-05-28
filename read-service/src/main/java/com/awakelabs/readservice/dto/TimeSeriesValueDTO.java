package com.awakelabs.readservice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TimeSeriesValueDTO {

    private Long time;
    private Long value;

}
