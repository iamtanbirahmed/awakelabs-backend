package com.awakelabs.writeservice.controller;

import com.awakelabs.writeservice.dto.VitalSignDTO;
import com.awakelabs.writeservice.service.VitalSignWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Arrays;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v0.1/write")
public class VitalSignWriteController {

    private final VitalSignWriteService vitalSignWriteService;

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED, code = HttpStatus.CREATED)
    public @ResponseBody
    VitalSignDTO saveVitalSign(@RequestBody VitalSignDTO vitalSignDTO) {
        vitalSignWriteService.saveVitalSign(vitalSignDTO);
        return vitalSignDTO;

    }


    @SneakyThrows
    @PostMapping("/json")
    @ResponseStatus(value = HttpStatus.CREATED, code = HttpStatus.CREATED)
    public @ResponseBody
    String saveVitalSignFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream in = getClass().getResourceAsStream("/data.json");
        VitalSignDTO[] vitalSignDTOS = objectMapper.readValue(in, VitalSignDTO[].class);

        Arrays.asList(vitalSignDTOS).stream().forEach(vitalSignDTO -> {
            System.out.println(vitalSignDTOS);
            this.vitalSignWriteService.saveVitalSign(vitalSignDTO);

        });


        return "Entire Json file was loaded successfully in the database";


    }

//    @PostMapping("/cache")
//    public @ResponseBody
//    VitalSignDTO saveInCache(@RequestBody VitalSignDTO vitalSignDTO) {
//        this.vitalSignService.saveInCache(vitalSignDTO);
//        return vitalSignDTO;
//    }
}
