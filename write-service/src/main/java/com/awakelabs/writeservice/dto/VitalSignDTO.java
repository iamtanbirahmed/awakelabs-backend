package com.awakelabs.writeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VitalSignDTO {

    /**
     * The DTO to receive the payload
     */
    private Integer id;
    private DataDTO data;

}
