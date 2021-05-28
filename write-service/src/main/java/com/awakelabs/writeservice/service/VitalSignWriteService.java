package com.awakelabs.writeservice.service;


import com.awakelabs.writeservice.dto.HeartRateDTO;
import com.awakelabs.writeservice.dto.VitalSignDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VitalSignWriteService {


    //    @Value("${awakelabs.anxiety-level-threshold}")
    private final Double ANXIETY_LEVEL_THRESHOLD = 2.0;
    private CacheWriteService cacheWriteService;
    private NotificationService notificationService;
    private EncoderService encoderService;

    /**
     * method to transform the input data to save in persistent storage
     * @param vitalSignDTO
     */
    public void saveVitalSign(VitalSignDTO vitalSignDTO) {
        UUID newVitalSignId = this.encoderService.saveEncryptedVitalSign(vitalSignDTO);
        for (HeartRateDTO heartRateDTO : vitalSignDTO.getData().getHeartRates()) {
            // Send notification
            this.handleAnxietyLevel(heartRateDTO.getAnxietyLevel(), vitalSignDTO.getId());
            // save the heart rates
            this.encoderService.saveEncryptedHeartRate(heartRateDTO, newVitalSignId, vitalSignDTO.getId());
        }

    }

    /**
     * Demo method to simulate real-time notification if anxiety level exceed the threshold
     * @param anxietyLevel
     * @param patientId
     */

    private void handleAnxietyLevel(Double anxietyLevel, Integer patientId) {
        if (anxietyLevel > Double.valueOf(this.ANXIETY_LEVEL_THRESHOLD)) {
            try {
                this.notificationService.send(patientId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to save the phi values in redis cache (abandoned approach due to privacy concern)
     * @param vitalSignDTO
     */

    private void saveInCache(VitalSignDTO vitalSignDTO) {
        this.cacheWriteService.setHeartRates(vitalSignDTO.getData().getHeartRates(), vitalSignDTO.getId());
    }










    /**
     * Save the vital sign into database and cache
     * @param vitalSignDTO the DTO for the vital sign
     */

//    public void saveVitalSign(@NotNull VitalSignDTO vitalSignDTO) {
//        VitalSign vitalSign = this.modelMapper.map(vitalSignDTO.getData(), VitalSign.class);
//        vitalSign.setPatientId(vitalSignDTO.getId());
//        vitalSign.setBatteryLevel(vitalSignDTO.getData().getState().getBatteryLevel());
//
//        UUID newVitalSignId = this.saveEncryptedVitalSign(vitalSign);
//
//        Set<HeartRate> heartRates = new HashSet<HeartRate>();
//
//        for (HeartRateDTO heartRateDTO : vitalSignDTO.getData().getHeartRates()) {
//            HeartRate heartRate = this.modelMapper.map(heartRateDTO, HeartRate.class);
//            heartRate.setVitalSign(vitalSign.getId());
//            heartRate.setPatientId(vitalSign.getPatientId());
//            this.handleAnxietyLevel(heartRate.getAnxietyLevel(), vitalSign.getPatientId());
//            this.saveEncryptedHeartRate(heartRate, newVitalSignId);
//            heartRates.add(heartRate);
//        }
//
////        vitalSign.setHeartRates(heartRates);
//
////        this.saveInCache(vitalSignDTO);
//
//    }


}
