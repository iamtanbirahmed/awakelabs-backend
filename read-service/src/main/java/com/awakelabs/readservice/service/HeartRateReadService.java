package com.awakelabs.readservice.service;


import com.awakelabs.readservice.component.EncryptionKeyComponent;
import com.awakelabs.readservice.dto.HeartRateReadDTO;
import com.awakelabs.readservice.dto.TimeSeriesValueDTO;
import com.awakelabs.readservice.model.HeartRate;
import com.awakelabs.readservice.repository.HeartRateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class HeartRateReadService {

    private HeartRateRepository heartRateRepository;
    private CacheReadService cacheReadService;
    private ModelMapper modelMapper;
    private EncryptionKeyComponent encryptionKeyComponent;

    /**
     * Helper method placed for any data conversion required (Cache read. abandoned)
     * @param id
     * @param from
     * @param to
     * @return
     */
    public List<TimeSeriesValueDTO> getHeartRatesByRange(Integer id, Long from, Long to) {
        return this.cacheReadService.getHeartRatesByRange(id, from, to);

    }

    /**
     * This method transforms the raw Heart Rate PHI values from the data query into Json response.
     * @return json response object
     */
    public List<HeartRateReadDTO> getAllHeartRates() {

        List<HeartRate> heartRates = this.heartRateRepository.findAllEncrypted(this.encryptionKeyComponent.getHeartRateSecret());
        List<HeartRateReadDTO> heartRateReadDTOS = new ArrayList<>();
        heartRates.stream().forEach(heartRate -> {
            HeartRateReadDTO heartRateReadDTO = modelMapper.map(heartRate, HeartRateReadDTO.class);
            // Split to find individual PHI values from data stream
            String[] phiValues = heartRate.getPhiValues().split("#");

            heartRateReadDTO.setAnxietyLevel(Double.valueOf(phiValues[0]));
            heartRateReadDTO.setHeartRate(Double.valueOf(phiValues[1]));
            heartRateReadDTO.setRrInterval(Double.valueOf(phiValues[2]));
            heartRateReadDTOS.add(heartRateReadDTO);

        });

        return heartRateReadDTOS;
    }




    /**
     * @param id
     * @return
     */
//    public List<HeartRate> getAllHeartRatesByPatientId(Integer id) {
//        return heartRateRepository.findAllByVitalSign_PatientId(id);
//    }


}
