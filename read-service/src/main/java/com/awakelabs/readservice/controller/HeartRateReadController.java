package com.awakelabs.readservice.controller;


import com.awakelabs.readservice.component.EncryptionKeyComponent;
import com.awakelabs.readservice.dto.HeartRateReadDTO;
import com.awakelabs.readservice.dto.TimeSeriesValueDTO;
import com.awakelabs.readservice.service.HeartRateReadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/v0.1/heart-rates/read")
public class HeartRateReadController {

    private HeartRateReadService heartRateReadService;
    private EncryptionKeyComponent encryptionKeyComponent;

    @GetMapping("/pass")
    public @ResponseBody
    String getPassword() {
        return this.encryptionKeyComponent.getHeartRateSecret() + " " + this.encryptionKeyComponent.getVitalSignSecret();
    }

    @GetMapping("/{from}/{to}/{id}")
    @ResponseStatus(value = HttpStatus.FOUND, code = HttpStatus.FOUND)
    public List<TimeSeriesValueDTO> getHeartRates(@PathVariable Long from, @PathVariable Long to, @PathVariable Integer id) {
        return this.heartRateReadService.getHeartRatesByRange(id, from, to);

    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.FOUND, code = HttpStatus.FOUND)
    public List<HeartRateReadDTO> getHeartRatesEncrypted() {
        return this.heartRateReadService.getAllHeartRates();

    }

    //    @GetMapping("/patient/{id}")
//    @ResponseStatus(value = HttpStatus.FOUND, code = HttpStatus.FOUND)
//    public @ResponseBody
//    List<HeartRate> getAllHeartRateByPatientId(@PathVariable Integer id) {
//        return this.heartRateReadService.getAllHeartRatesByPatientId(id);
//    }


}
