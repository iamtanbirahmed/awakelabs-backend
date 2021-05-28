package com.awakelabs.readservice.controller;


import com.awakelabs.readservice.dto.VitalSignReadDTO;
import com.awakelabs.readservice.service.VitalSignReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/v0.1/read")
public class VitalSignReadController {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private String portNumber;

    @Autowired
    private VitalSignReadService vitalSignReadService;

    @GetMapping("/greeting")
    @ResponseStatus(value = HttpStatus.FOUND, code = HttpStatus.FOUND)
    public @ResponseBody
    String test() {
        return "Greeting from " + appName + ":" + portNumber;
    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.FOUND, code = HttpStatus.FOUND)
    public @ResponseBody
    List<VitalSignReadDTO> getVitalSign() {
        return this.vitalSignReadService.getAllVitalSign();
    }

}
